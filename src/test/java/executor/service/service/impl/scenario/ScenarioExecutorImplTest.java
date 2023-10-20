package executor.service.service.impl.scenario;

import executor.service.model.request.Scenario;
import executor.service.model.request.StepRequest;
import executor.service.service.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ScenarioExecutorImpl} class is an
 * implementation of the {@link ScenarioExecutor}. This class contains unit tests to verify that
 * {@code ScenarioExecutorImpl} is working correctly.
 *
 * @author Stanislav Khytrych, Oleksandr Tuleninov
 * @version 01
 */
public class ScenarioExecutorImplTest {

    @Test
    public void testWorkExecute() {
        Scenario scenario = getScenario();
        Map<StepRequest, Boolean> stepResults = new HashMap<>();
        stepResults.put(scenario.getSteps().get(0), true);
        stepResults.put(scenario.getSteps().get(1), true);
        stepResults.put(scenario.getSteps().get(2), true);

        StepExecutionClickCss stepExecutionClickCss = mock(StepExecutionClickCss.class);
        StepExecutionSleep stepExecutionSleep = mock(StepExecutionSleep.class);
        StepExecutionClickXpath stepExecutionClickXpath = mock(StepExecutionClickXpath.class);
        ScenarioResultService scenarioResultService = mock(ScenarioResultService.class);
        WebDriver webDriver = mock(WebDriver.class);

        doNothing().when(stepExecutionClickCss).step(any(WebDriver.class), any(StepRequest.class));
        doNothing().when(stepExecutionSleep).step(any(WebDriver.class), any(StepRequest.class));
        doNothing().when(stepExecutionClickXpath).step(any(WebDriver.class), any(StepRequest.class));

        ScenarioExecutor scenarioExecutor = new ScenarioExecutorImpl(
                stepExecutionClickCss, stepExecutionSleep, stepExecutionClickXpath, scenarioResultService);
        scenarioExecutor.execute(scenario, webDriver);

        verify(stepExecutionClickCss, times(1)).step(any(WebDriver.class), any(StepRequest.class));
        verify(stepExecutionSleep, times(1)).step(any(WebDriver.class), any(StepRequest.class));
        verify(stepExecutionClickXpath, times(1)).step(any(WebDriver.class), any(StepRequest.class));
        verifyNoMoreInteractions(stepExecutionClickCss);
        verifyNoMoreInteractions(stepExecutionSleep);
        verifyNoMoreInteractions(stepExecutionClickXpath);

        verify(scenarioResultService).create(scenario, stepResults);
        verify(webDriver).close();
    }

    private Scenario getScenario() {
        return new Scenario(
                "test scenario 1",
                "https://info.cern.ch",
                Arrays.asList(
                        new StepRequest("clickCss", "body > ul > li:nth-child(1) > a"),
                        new StepRequest("sleep", "5"),
                        new StepRequest("clickXpath", "/html/body/p")));
    }
}
