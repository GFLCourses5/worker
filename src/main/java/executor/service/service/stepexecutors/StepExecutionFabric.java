package executor.service.service.stepexecutors;

public interface StepExecutionFabric {

  StepExecution getExecutionClickCss();

  StepExecution getExecutionSleep();

  StepExecution getExecutionXpath();
}
