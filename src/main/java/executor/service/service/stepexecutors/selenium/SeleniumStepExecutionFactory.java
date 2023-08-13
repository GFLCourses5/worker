package executor.service.service.stepexecutors.selenium;

import executor.service.service.stepexecutors.StepExecution;
import executor.service.service.stepexecutors.StepExecutionFabric;

public class SeleniumStepExecutionFactory implements StepExecutionFabric {

  private final StepExecutionClickCss css;
  private final StepExecutionSleep sleep;
  private final StepExecutionClickXpath xpath;

  public SeleniumStepExecutionFactory(StepExecutionClickCss css, StepExecutionSleep sleep,
      StepExecutionClickXpath xpath) {
    this.css = css;
    this.sleep = sleep;
    this.xpath = xpath;
  }

  @Override
  public StepExecution getExecutionClickCss() {
    return css;
  }

  @Override
  public StepExecution getExecutionSleep() {
    return sleep;
  }

  @Override
  public StepExecution getExecutionXpath() {
    return xpath;
  }
}
