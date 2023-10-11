package executor.service.service.impl.stepExecution;

import executor.service.model.Step;
import executor.service.service.StepExecutionClickCss;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

/**
 * The {@code StepExecutionClickCssImpl} class implements the {@link StepExecutionClickCss} interface
 * for executing a step that involves clicking an HTML element by CSS selector.
 * <p>
 *
 * @author Anton Sokolsky
 * @version 01
 * @see executor.service.model.Step
 */
@Service
public class StepExecutionClickCssImpl implements StepExecutionClickCss {

    @Override
    public String getStepAction() {
        return "clickCss";
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String value = step.getValue();
        WebElement element = webDriver.findElement(By.cssSelector(value));
        element.click();
    }
}
