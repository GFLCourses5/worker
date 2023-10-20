package executor.service.service.impl.scenario;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.request.StepRequest;
import executor.service.model.request.Scenario;
import executor.service.service.*;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ScenarioExecutorImpl} class implements the {@link ScenarioExecutor} interface
 * and selects a StepExecution for each Step and runs it.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see StepExecutionClickCss
 * @see StepExecutionSleep
 * @see StepExecutionClickXpath
 * @see ScenarioResultService
 * @see Scenario
 * @see WebDriver
 * @see StepRequest
 * @see NoSuchElementException
 * @see StepExecutionException
 * @see ElementClickInterceptedException
 */
@Service
public class ScenarioExecutorImpl implements ScenarioExecutor {

    private static final Logger log = LoggerFactory.getLogger(ScenarioExecutorImpl.class);

    private final StepExecutionClickCss stepExecutionClickCss;
    private final StepExecutionSleep stepExecutionSleep;
    private final StepExecutionClickXpath stepExecutionClickXpath;
    private final ScenarioResultService scenarioResultService;

    public ScenarioExecutorImpl(StepExecutionClickCss stepExecutionClickCss,
                                StepExecutionSleep stepExecutionSleep,
                                StepExecutionClickXpath stepExecutionClickXpath,
                                ScenarioResultService scenarioResultService) {
        this.stepExecutionClickCss = stepExecutionClickCss;
        this.stepExecutionSleep = stepExecutionSleep;
        this.stepExecutionClickXpath = stepExecutionClickXpath;
        this.scenarioResultService = scenarioResultService;
    }

    @Override
    public void execute(Scenario scenario, WebDriver webDriver) {
        log.info("Execution of scenario: " + scenario.getName() + " is started");
        Map<StepRequest, Boolean> stepResults = new HashMap<>();

        webDriver.get(scenario.getSite());

        for (StepRequest stepRequest : scenario.getSteps()) {
            try {
                String action = stepRequest.action();
                switch (action) {
                    case "clickCss" -> {
                        stepExecutionClickCss.step(webDriver, stepRequest);
                        stepResults.put(stepRequest, true);
                    }
                    case "sleep" -> {
                        stepExecutionSleep.step(webDriver, stepRequest);
                        stepResults.put(stepRequest, true);
                    }
                    case "clickXpath" -> {
                        stepExecutionClickXpath.step(webDriver, stepRequest);
                        stepResults.put(stepRequest, true);
                    }
                    default -> {
                        log.error("Invalid step action: " + action);
                        stepResults.put(stepRequest, true);
                    }
                }
            } catch (NoSuchElementException | StepExecutionException | ElementClickInterceptedException e) {
                log.error("Scenario: " + scenario.getName() + " - step failed: " + stepRequest);
                stepResults.put(stepRequest, false);
            }
        }

        webDriver.close();

        log.info("Execution of scenario: " + scenario.getName() + " is finished");
        scenarioResultService.create(scenario, stepResults);
    }
}
