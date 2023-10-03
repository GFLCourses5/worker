package executor.service.service.impl;

import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.model.StepTypes;
import executor.service.service.ScenarioExecutor;
import executor.service.service.StepExecutionClickCss;
import executor.service.service.StepExecutionClickXpath;
import executor.service.service.StepExecutionSleep;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ScenarioExecutorImpl} class is an
 * implementation of the {@link ScenarioExecutor}. This class contains unit tests to verify that
 * {@code ScenarioExecutorImpl} is working correctly.
 *
 * @author Stanislav Khytrych
 * @version 01
 */
public class ScenarioExecutorImplTest {

  @Test
  public void testWorkExecute() {
    StepExecutionClickCss stepExecutionClickCss = mock(StepExecutionClickCss.class);
    StepExecutionSleep stepExecutionSleep = mock(StepExecutionSleep.class);
    StepExecutionClickXpath stepExecutionClickXpath = mock(StepExecutionClickXpath.class);
    WebDriver webDriver = mock(WebDriver.class);

    doNothing().when(stepExecutionClickCss).step(any(WebDriver.class), any(Step.class));
    doNothing().when(stepExecutionSleep).step(any(WebDriver.class), any(Step.class));
    doNothing().when(stepExecutionClickXpath).step(any(WebDriver.class), any(Step.class));

    Scenario scenario = getScenario();

    ScenarioExecutor scenarioExecutor = new ScenarioExecutorImpl(
        stepExecutionClickCss, stepExecutionSleep, stepExecutionClickXpath);
    scenarioExecutor.execute(scenario, webDriver);

    verify(stepExecutionClickCss, times(1)).step(any(WebDriver.class), any(Step.class));
    verify(stepExecutionSleep, times(1)).step(any(WebDriver.class), any(Step.class));
    verify(stepExecutionClickXpath, times(1)).step(any(WebDriver.class), any(Step.class));
    verifyNoMoreInteractions(stepExecutionClickCss);
    verifyNoMoreInteractions(stepExecutionSleep);
    verifyNoMoreInteractions(stepExecutionClickXpath);
  }

  private Scenario getScenario() {
    return new Scenario(
        "test scenario 1",
        "http://info.cern.ch",
        Arrays.asList(
            new Step(StepTypes.CLICK_CSS, "body > ul > li:nth-child(1) > a"),
            new Step(StepTypes.SLEEP, "5000:15000"),
            new Step(StepTypes.CLICK_XPATH, "/html/body/p")));
  }
}