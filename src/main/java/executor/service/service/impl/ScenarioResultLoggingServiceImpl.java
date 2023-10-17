package executor.service.service.impl;

import executor.service.exceptions.ScenarioResultExceptions;
import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.model.response.ScenarioResult;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.model.response.StepResult;
import executor.service.repository.ScenarioResultRepository;
import executor.service.repository.StepRepository;
import executor.service.repository.StepResultRepository;
import executor.service.service.ScenarioResultLoggingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@code ScenarioResultLoggingServiceImpl} class implements the {@link ScenarioResultLoggingService} interface and
 * working with scenario results ({@link ScenarioResult}) and step results ({@link StepResult}).
 * <p>
 *
 * @author Oleksandr Antonenko, Oleksandr Tuleninov
 * @version 01
 * @see ScenarioResultRepository
 * @see StepResultRepository
 * @see Scenario
 * @see Step
 */
@Service
public class ScenarioResultLoggingServiceImpl implements ScenarioResultLoggingService {

    private final ScenarioResultRepository scenarioResultRepository;
    private final StepResultRepository stepResultRepository;
    private final StepRepository stepRepository;
    private final ScenarioResultLoggingService scenarioResultLoggingService;

    public ScenarioResultLoggingServiceImpl(ScenarioResultRepository scenarioResultRepository,
                                            StepResultRepository stepResultRepository,
                                            StepRepository stepRepository,
                                            ScenarioResultLoggingService scenarioResultLoggingService) {
        this.scenarioResultRepository = scenarioResultRepository;
        this.stepResultRepository = stepResultRepository;
        this.stepRepository = stepRepository;
        this.scenarioResultLoggingService = scenarioResultLoggingService;
    }

    @Override
    @Transactional
    public void create(Scenario scenario, Map<Step, Boolean> stepResults) {
        saveScenarioResult(scenario, stepResults);
    }

    private void saveScenarioResult(Scenario scenario, Map<Step, Boolean> stepResults) {
        scenarioResultRepository.save(createScenarioResult(scenario, stepResults));
    }

    private ScenarioResult createScenarioResult(Scenario scenario, Map<Step, Boolean> stepResults) {
        return new ScenarioResult(
                scenario.getUserId(),
                scenario.getName(),
                scenario.getSite(),
                getStepResults(scenario, stepResults),
                OffsetDateTime.now()
        );
    }

    private Set<StepResult> getStepResults(Scenario scenario, Map<Step, Boolean> stepResults) {
        List<Step> steps = scenario.getSteps();
        Set<StepResult> listStepResults = new LinkedHashSet<>();
        for (Step step : steps) {
            Boolean resultFromStep = stepResults.get(step);
            StepResult stepResult = saveStepResult(step, resultFromStep);
            listStepResults.add(stepResult);
        }
        return listStepResults;
    }

    private StepResult saveStepResult(Step step, Boolean resultFromStep) {
        StepResult stepResult = createStepResult(step, resultFromStep);
        stepResultRepository.save(stepResult);
        return stepResult;
    }

    private StepResult createStepResult(Step step, Boolean resultFromStep) {
        Step savedStep = saveStep(step);
        return new StepResult(
                savedStep,
                resultFromStep
        );
    }

    private Step saveStep(Step step) {
        String action = step.getAction();
        String value = step.getValue();
        Optional<Step> stepByActionAndValue = stepRepository.findStepByActionAndValue(action, value);
        return stepByActionAndValue.orElseGet(() -> stepRepository.save(step));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer userId) {
        List<ScenarioResult> scenarioResults = scenarioResultRepository.findAllByUserId(userId);
        List<ScenarioResult> sortedScenarioResults = sorted(scenarioResults);
        return sortedScenarioResults
                .stream()
                .map(ScenarioResultResponse::formScenarioResult)
                .toList();
    }

    private List<ScenarioResult> sorted(List<ScenarioResult> scenarioResults) {
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
        if (!scenarioResultRepository.existsById(scenarioId))
            throw ScenarioResultExceptions.scenarioNotFound(scenarioId);
        scenarioResultRepository.deleteById(scenarioId);
    }
}
