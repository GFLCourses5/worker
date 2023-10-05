package executor.service.service.impl.stepExecution;

import executor.service.model.Step;
import executor.service.model.StepTypes;
import executor.service.service.StepExecutionClickCss;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code StepExecutionClickCssImpl} class implements the {@link StepExecutionClickCss} interface
 * for executing a step that involves clicking an HTML element by CSS selector.
 * <p>
 *
 * @author Anton Sokolsky
 * @version 01
 * @see executor.service.model.StepTypes
 */
public class StepExecutionClickCssImpl implements StepExecutionClickCss {

    private static final Logger log = LoggerFactory.getLogger(StepExecutionSleepImpl.class);

    @Override
    public String getStepAction() {
        return StepTypes.CLICK_CSS.getName();
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String value = step.getValue();
        try {
            WebElement element = webDriver.findElement(By.cssSelector(value));
            element.click();
        } catch (NoSuchElementException e) {
            log.error("Invalid step value: " + value);
        }
    }
}
