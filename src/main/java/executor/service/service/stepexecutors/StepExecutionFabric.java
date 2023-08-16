package executor.service.service.stepexecutors;

public interface StepExecutionFabric {

   StepExecution getStepExecutor(String stepAction);

}
