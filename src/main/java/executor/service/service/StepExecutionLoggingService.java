package executor.service.service;

import executor.service.model.Step;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.model.response.StepResult;

import java.util.List;

public interface StepExecutionLoggingService {

    StepResult loggingStep(ScenarioResultResponse scenarioResponse, Step step, Boolean result);

    List<StepResult> getAllStepResult();
}
