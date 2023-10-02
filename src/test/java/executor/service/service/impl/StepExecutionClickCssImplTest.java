package executor.service.service.impl;

import executor.service.model.Step;
import executor.service.model.StepTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StepExecutionClickCssImplTest {

    private StepExecutionClickCssImpl clickCss;

    @BeforeEach
    public void setUp() {
        clickCss = new StepExecutionClickCssImpl();
    }

    @Test
    public void testGetStepAction() {
        String action = clickCss.getStepAction();
        assertEquals("clickCss", action);
    }

    @Test
    public void testStepClickElementWhenFound() {
        WebDriver webDriver = mock(WebDriver.class);
        Step step = new Step(StepTypes.CLICK_CSS, "css_expression");
        WebElement webElement = mock(WebElement.class);
        when(webDriver.findElement(By.cssSelector(step.getValue()))).thenReturn(webElement);
        clickCss.step(webDriver, step);
        verify(webElement, times(1)).click();
    }

    @Test
    public void testStepHandlesNoSuchElementException() {
        WebDriver webDriver = mock(WebDriver.class);
        Step step = new Step(StepTypes.CLICK_CSS, "non_existent_css_expression");
        when(webDriver.findElement(By.cssSelector(step.getValue()))).thenThrow(NoSuchElementException.class);
        clickCss.step(webDriver, step);
    }

}