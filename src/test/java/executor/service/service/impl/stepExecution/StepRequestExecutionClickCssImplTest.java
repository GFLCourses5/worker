package executor.service.service.impl.stepExecution;

import executor.service.model.request.StepRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code StepExecutionClockCssImpl} class
 * is an implementation of the {@link executor.service.service.StepExecutionClickCss}.
 * This class contains unit tests to verify that {@code StepExecutionClickCssImp} is working correctly.
 *
 *  @author Alexander Antonenko
 *  @version 01
 * */
class StepRequestExecutionClickCssImplTest {

    private StepExecutionClickCssImpl clickCss;

    @BeforeEach
    public void setUp() {
        clickCss = new StepExecutionClickCssImpl();
    }

    @AfterEach
    public void tearDown() {
        clickCss = null;
    }

    @Test
    public void testGetStepAction() {
        String action = clickCss.getStepAction();
        assertEquals("clickCss", action);
    }

    @Test
    public void testStepClickElementWhenFound() {
        WebDriver webDriver = mock(WebDriver.class);
        StepRequest stepRequest = new StepRequest("clickCss", "css_expression");
        WebElement webElement = mock(WebElement.class);
        when(webDriver.findElement(By.cssSelector(stepRequest.value()))).thenReturn(webElement);
        clickCss.step(webDriver, stepRequest);
        verify(webElement, times(1)).click();
    }
}
