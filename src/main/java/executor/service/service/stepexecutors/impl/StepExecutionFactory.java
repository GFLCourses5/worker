package executor.service.service.stepexecutors.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.service.stepexecutors.StepExecution;
import executor.service.service.stepexecutors.StepExecutionFabric;
import java.util.List;

public class StepExecutionFactory implements StepExecutionFabric {

  private final List<StepExecution> stepExecutors;

  public StepExecutionFactory(List<StepExecution> stepExecutors) {
    this.stepExecutors = stepExecutors;
  }

  @Override
  public StepExecution getStepExecutor(String stepAction) {
    return stepExecutors.stream().filter(s -> s.getStepAction().compareTo(stepAction) == 0)
        .findFirst().orElseThrow(() -> new StepExecutionException("Unsupported type execution!"));
  }


}
