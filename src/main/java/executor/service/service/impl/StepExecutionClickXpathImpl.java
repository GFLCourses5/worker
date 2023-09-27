package executor.service.service.impl;

import executor.service.model.Step;
import executor.service.model.StepTypes;
import executor.service.service.StepExecutionClickXpath;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StepExecutionClickXpathImpl implements StepExecutionClickXpath {

    @Override
    public String getStepAction() {
        return StepTypes.CLICK_XPATH.getName();
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        try {
            WebElement element = webDriver.findElement(By.xpath(step.getValue()));
            element.click();
        } catch (NoSuchElementException e) {
            System.out.println("this is error");
        }
    }
}
