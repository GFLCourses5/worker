package executor.service.service.impl;

import executor.service.model.Scenario;
import executor.service.model.response.ScenarioResult;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.repository.ScenarioResultRepository;
import executor.service.repository.StepExecutionLoggingRepository;
import executor.service.service.ScenarioResultService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ScenarioResultServiceImpl implements ScenarioResultService {

    private final ScenarioResultRepository scenarioResultRepository;

    public ScenarioResultServiceImpl(ScenarioResultRepository scenarioResultRepository) {
        this.scenarioResultRepository = scenarioResultRepository;
    }

    @Override
    @Transactional
    public ScenarioResultResponse createScenarioResult(Scenario scenario) {
            ScenarioResult scenarioResult = new ScenarioResult();
            scenarioResult.setUserId(0);
            scenarioResult.setName(scenario.getName());
            scenarioResult.setSite(scenario.getSite());
            scenarioResult.setStepsResults(null);
            scenarioResult.setExecutedAt(OffsetDateTime.now());
            scenarioResultRepository.save(scenarioResult);
        return ScenarioResultResponse.formScenarioResult(scenarioResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer id) {
        return scenarioResultRepository
                .findAllByUserId(id)
                .stream()
                .map(ScenarioResultResponse::formScenarioResult)
                .toList();
    }

    @Override
    public ScenarioResult findById(Integer id) {
        return scenarioResultRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
