package executor.service.service.stepexecutors.impl;

import executor.service.model.entity.Step;
import executor.service.model.entity.StepTypes;
import executor.service.service.stepexecutors.StepExecutionClickCss;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StepExecutionClickCssImpl implements StepExecutionClickCss {

  @Override
  public String getStepAction() {
    return StepTypes.CLICK_CSS.getName();
  }

  @Override
  public void step(WebDriver webDriver, Step step) {
    WebElement element = webDriver.findElement(By.cssSelector(step.getValue()));
    element.click();
  }
}
