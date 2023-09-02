package executor.service.service.impl;

import executor.service.model.Step;
import executor.service.model.enums.StepAction;
import executor.service.service.StepExecution;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StepExecutionClickCss implements StepExecution {

    private static final Logger log = LoggerFactory.getLogger(StepExecution.class);
    @Override
    public String getStepAction() {
        return StepAction.CLICK_CSS.getName();
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        log.info("Executing {} {}", step.getAction(), step.getValue());
        String cssSelector = step.getValue();
        WebElement element = webDriver.findElement(By.cssSelector(cssSelector));
        element.click();
    }
}
