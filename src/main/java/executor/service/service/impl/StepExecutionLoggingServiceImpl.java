package executor.service.service.impl;

import executor.service.model.Step;
import executor.service.model.response.ScenarioResult;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.model.response.StepResult;
import executor.service.repository.StepExecutionLoggingRepository;
import executor.service.service.ScenarioResultService;
import executor.service.service.StepExecutionLoggingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StepExecutionLoggingServiceImpl implements StepExecutionLoggingService {

    private final StepExecutionLoggingRepository stepExecutionLoggingRepository;
    private final ScenarioResultService scenarioResultService;

    public StepExecutionLoggingServiceImpl(StepExecutionLoggingRepository stepExecutionLoggingRepository,
                                           ScenarioResultService scenarioResultService) {
        this.stepExecutionLoggingRepository = stepExecutionLoggingRepository;
        this.scenarioResultService = scenarioResultService;
    }

    @Override
    @Transactional
    public StepResult loggingStep(ScenarioResultResponse scenarioResponse, Step step, Boolean result) {
        StepResult stepResult = new StepResult();
        stepResult.setAction(step.getAction());
        stepResult.setValue(step.getValue());
        stepResult.setIsPassed(result);
        stepResult.setScenarioId(scenarioResponse.id());
        return stepExecutionLoggingRepository.save(stepResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StepResult> getAllStepResult() {
        return stepExecutionLoggingRepository.findAll();
    }
}
