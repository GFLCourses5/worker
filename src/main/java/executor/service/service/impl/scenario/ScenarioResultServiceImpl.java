package executor.service.service.impl.scenario;

import executor.service.exceptions.ScenarioResultExceptions;
import executor.service.model.ScenarioResult;
import executor.service.model.Step;
import executor.service.model.StepResult;
import executor.service.model.request.Scenario;
import executor.service.model.request.StepRequest;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.repository.ScenarioResultRepository;
import executor.service.repository.StepRepository;
import executor.service.repository.StepResultRepository;
import executor.service.service.ScenarioResultService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@code ScenarioResultLoggingServiceImpl} class implements the {@link ScenarioResultService} interface and
 * working with scenario results ({@link ScenarioResult}) and step results ({@link StepResult}).
 * <p>
 *
 * @author Oleksandr Antonenko, Oleksandr Tuleninov
 * @version 01
 * @see ScenarioResultRepository
 * @see StepResultRepository
 * @see Scenario
 * @see StepRequest
 * @see ScenarioResultExceptions
 */
@Service
public class ScenarioResultServiceImpl implements ScenarioResultService {

    private final ScenarioResultRepository scenarioResultRepository;
    private final StepResultRepository stepResultRepository;
    private final StepRepository stepRepository;

    public ScenarioResultServiceImpl(ScenarioResultRepository scenarioResultRepository,
                                     StepResultRepository stepResultRepository,
                                     StepRepository stepRepository) {
        this.scenarioResultRepository = scenarioResultRepository;
        this.stepResultRepository = stepResultRepository;
        this.stepRepository = stepRepository;
    }

    @Override
    @Transactional
    public void create(Scenario scenario, Map<StepRequest, Boolean> stepResults) {
        saveScenarioResult(scenario, stepResults);
    }

    private void saveScenarioResult(Scenario scenario, Map<StepRequest, Boolean> stepResults) {
        scenarioResultRepository.save(createScenarioResult(scenario, stepResults));
    }

    private ScenarioResult createScenarioResult(Scenario scenario, Map<StepRequest, Boolean> stepResults) {
        return new ScenarioResult(
                scenario.getUserId(),
                scenario.getName(),
                scenario.getSite(),
                getStepResults(scenario, stepResults),
                OffsetDateTime.now()
        );
    }

    private Set<StepResult> getStepResults(Scenario scenario, Map<StepRequest, Boolean> stepResults) {
        List<StepRequest> stepRequests = scenario.getSteps();
        Set<StepResult> listStepResults = new LinkedHashSet<>();
        for (StepRequest stepRequest : stepRequests) {
            Boolean resultFromStep = stepResults.get(stepRequest);
            StepResult stepResult = saveStepResult(stepRequest, resultFromStep);
            listStepResults.add(stepResult);
        }
        return listStepResults;
    }

    private StepResult saveStepResult(StepRequest stepRequest, Boolean resultFromStep) {
        StepResult stepResult = createStepResult(stepRequest, resultFromStep);
        stepResultRepository.save(stepResult);
        return stepResult;
    }

    private StepResult createStepResult(StepRequest stepRequest, Boolean resultFromStep) {
        Step savedStep = saveStepEntity(stepRequest);
        return new StepResult(
                savedStep,
                resultFromStep
        );
    }

    private Step saveStepEntity(StepRequest stepRequest) {
        String action = stepRequest.action();
        String value = stepRequest.value();
        Optional<Step> stepByActionAndValue = stepRepository.findStepByActionAndValue(action, value);
        Step step = getStepEntity(stepRequest);
        return stepByActionAndValue.orElseGet(() -> stepRepository.save(step));
    }

    private Step getStepEntity(StepRequest stepRequest) {
        return new Step(
                stepRequest.action(),
                stepRequest.value()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer userId, Pageable pageable) {
        Page<ScenarioResult> scenarioResults = scenarioResultRepository.findAllByUserId(userId, pageable);
        Page<ScenarioResult> sortedScenarioResults = sorted(scenarioResults);
        return sortedScenarioResults
                .map(ScenarioResultResponse::formScenarioResult);
    }

    private Page<ScenarioResult> sorted(Page<ScenarioResult> scenarioResults) {
        for (ScenarioResult scenarioResult : scenarioResults) {
            Set<StepResult> collect = scenarioResult.getStepsResults().stream()
                    .sorted(Comparator.comparing(StepResult::getId))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            scenarioResult.setStepsResults(collect);
        }
        return scenarioResults;
    }

    @Override
    @Transactional
    public void deleteById(Integer scenarioId) {
        ScenarioResult scenarioResult = scenarioResultRepository.findById(scenarioId)
                .orElseThrow(() -> ScenarioResultExceptions.scenarioNotFound(scenarioId));
        stepResultRepository.deleteAll(scenarioResult.getStepsResults());
        scenarioResultRepository.deleteById(scenarioId);
    }
}
