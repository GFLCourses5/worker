package executor.service.service.stepexecutors.impl;

import executor.service.model.entity.Step;
import executor.service.model.entity.StepTypes;
import executor.service.service.stepexecutors.StepExecutionClickXpath;
import org.openqa.selenium.WebDriver;

public class StepExecutionClickXpathImpl implements StepExecutionClickXpath {

  @Override
  public String getStepAction() {
    return StepTypes.CLICK_XPATH.getName();
  }

  @Override
  public void step(WebDriver webDriver, Step step) {

  }
}
