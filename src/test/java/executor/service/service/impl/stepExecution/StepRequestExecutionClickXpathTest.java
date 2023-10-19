package executor.service.service.impl.stepExecution;

import executor.service.model.request.StepRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code StepExecutionClickXpathImpl} class is an
 * implementation of the {@link StepExecutionClickXpathImpl}. This class contains unit tests to verify that
 * {@code StepExecutionClickXpathImpl} is working correctly.
 *
 * @author Dima Silenko
 * @version 01
 */
public class StepRequestExecutionClickXpathTest {

    private StepExecutionClickXpathImpl clickXpath;

    @BeforeEach
    public void setUp() {
        clickXpath = new StepExecutionClickXpathImpl();
    }

    @Test
    public void testGetStepAction() {
        String stepAction = clickXpath.getStepAction();
        assertEquals("clickXpath", stepAction);
    }

    @Test
    public void testStep_ClicksElementWhenFound() {
        WebDriver webDriver = mock(WebDriver.class);
        StepRequest stepRequest = new StepRequest("clickXpath", "xpath_expression");
        WebElement mockElement = mock(WebElement.class);

        when(webDriver.findElement(By.xpath(stepRequest.value()))).thenReturn(mockElement);

        clickXpath.step(webDriver, stepRequest);
        verify(mockElement, times(1)).click();
    }
}
