package executor.service.service.impl.stepExecution;

import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
public class StepExecutionClickXpathTest {

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
        Step step = new Step("clickXpath", "xpath_expression");
        WebElement mockElement = mock(WebElement.class);

        when(webDriver.findElement(By.xpath(step.getValue()))).thenReturn(mockElement);

        clickXpath.step(webDriver, step);
        verify(mockElement, times(1)).click();
    }

    @Test
    public void testStep_HandlesNoSuchElementException() {
        WebDriver webDriver = mock(WebDriver.class);
        Step step = new Step("clickXpath", "non_existent_xpath_expression");
        when(webDriver.findElement(By.xpath(step.getValue()))).thenThrow(NoSuchElementException.class);

        clickXpath.step(webDriver, step);
    }
}
