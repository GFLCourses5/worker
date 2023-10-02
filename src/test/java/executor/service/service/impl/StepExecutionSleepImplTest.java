package executor.service.service.impl;

import executor.service.model.Step;
import executor.service.model.StepTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for testing the functionality of the {@code StepExecutionSleepImpl} class
 * is an implementation of the {@link executor.service.service.StepExecutionSleep}.
 * This class contains unit tests to verify that {@code StepExecutionSleepImpl} is working correctly.
 *
 * @author Alexander Antonenko
 * @version 01
 */
class StepExecutionSleepImplTest {

    private static final long EXPECTED_DURATION = 3000;
    private static final long MAX_ALLOWABLE_DELAY = 100;
    private StepExecutionSleepImpl stepExecutionSleep;

    @BeforeEach
    public void setUp() {
        stepExecutionSleep = new StepExecutionSleepImpl();
    }

    @AfterEach
    public void tearDown() {
        stepExecutionSleep = null;
    }

    @Test
    public void testGetStepAction() {
        String action = stepExecutionSleep.getStepAction();
        assertEquals(StepTypes.SLEEP.getName(), action);
    }

    @Test
    public void testStepSleepForSpecifiedDuration() {
        WebDriver webDriver = mock(WebDriver.class);
        Step step = new Step(StepTypes.SLEEP, "3");

        assertTimeout(Duration.ofMillis(EXPECTED_DURATION + MAX_ALLOWABLE_DELAY), () -> {
            long startTime = System.currentTimeMillis();
            stepExecutionSleep.step(webDriver, step);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            assertTrue(elapsedTime >= EXPECTED_DURATION);
        });
    }

    @Test
    public void testStepWithNegativeSleepDuration() {
        WebDriver webDriver = mock(WebDriver.class);
        Step step = new Step(StepTypes.SLEEP, "-1");

        assertThrows(IllegalArgumentException.class, () -> stepExecutionSleep.step(webDriver, step));
    }

    @Test
    public void testStepWithInvalidSleepValue() {
        WebDriver webDriver = mock(WebDriver.class);
        Step step = new Step(StepTypes.SLEEP, "invalid_value");

        assertThrows(NumberFormatException.class, () -> stepExecutionSleep.step(webDriver, step));
    }
}