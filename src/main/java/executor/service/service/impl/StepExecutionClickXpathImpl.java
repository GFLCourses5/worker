package executor.service.service.impl;

import executor.service.model.Step;
import executor.service.model.StepAction;
import executor.service.service.StepExecutionClickXpath;
import org.openqa.selenium.WebDriver;

public class StepExecutionClickXpathImpl implements StepExecutionClickXpath {

  @Override
  public String getStepAction() {
    return StepAction.CLICK_XPATH.getName();
  }

  @Override
  public void step(WebDriver webDriver, Step step) {

  }
}
