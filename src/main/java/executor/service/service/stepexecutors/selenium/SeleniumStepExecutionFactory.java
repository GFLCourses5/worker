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
  public StepExecution getStepExecutor(String stepAction) {
    SeleniumAction action = SeleniumAction.valueOf(stepAction);
    switch (action) {
      case CLICK_CSS:
        return css;
      case SLEEP:
        return sleep;
      case CLICK_XPATH:
        return xpath;
      default:
        throw new UnsupportedOperationException("");
    }

  }

  public enum SeleniumAction {
    CLICK_CSS("clickCss"),
    SLEEP("sleep"),
    CLICK_XPATH("clickXpath");

    private final String name;

    SeleniumAction(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

}
