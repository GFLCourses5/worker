package executor.service.service.impl.stepExecution;

import executor.service.model.Step;
import executor.service.model.StepTypes;
import executor.service.service.StepExecutionClickXpath;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code StepExecutionClickXpathImpl} class implements the {@link StepExecutionClickXpath} interface
 * for executing a step that involves clicking an HTML element by XPath.
 * <p>
 *
 * @author Yurii Kotsiuba
 * @version 01
 * @see executor.service.model.StepTypes
 */
public class StepExecutionClickXpathImpl implements StepExecutionClickXpath {

    private static final Logger log = LoggerFactory.getLogger(StepExecutionSleepImpl.class);

    @Override
    public String getStepAction() {
        return StepTypes.CLICK_XPATH.getName();
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String value = step.getValue();
        try {
            WebElement element = webDriver.findElement(By.xpath(value));
            element.click();
        } catch (NoSuchElementException e) {
            log.error("Invalid step value: " + value);
        }
    }
}
