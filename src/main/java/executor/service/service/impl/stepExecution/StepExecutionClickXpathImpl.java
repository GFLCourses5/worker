package executor.service.service.impl.stepExecution;

import executor.service.model.request.StepRequest;
import executor.service.service.StepExecutionClickXpath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

/**
 * The {@code StepExecutionClickXpathImpl} class implements the {@link StepExecutionClickXpath} interface
 * for executing a step that involves clicking an HTML element by XPath.
 * <p>
 *
 * @author Yurii Kotsiuba
 * @version 01
 * @see WebDriver
 * @see StepRequest
 */
@Service
public class StepExecutionClickXpathImpl implements StepExecutionClickXpath {

    @Override
    public String getStepAction() {
        return "clickXpath";
    }

    @Override
    public void step(WebDriver webDriver, StepRequest stepRequest) {
        String value = stepRequest.value();
        WebElement element = webDriver.findElement(By.xpath(value));
        element.click();
    }
}
