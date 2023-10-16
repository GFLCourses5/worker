package executor.service.service.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.service.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The {@code ScenarioExecutorImpl} class implements the {@link ScenarioExecutor} interface
 * and selects a StepExecution for each Step and runs it.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see StepExecutionClickCss
 * @see StepExecutionSleep
 * @see StepExecutionClickXpath
 * @see Scenario
 * @see WebDriver
 * @see Step
 * @see NoSuchElementException
 * @see StepExecutionException
 */
@Service
public class ScenarioExecutorImpl implements ScenarioExecutor {

    private static final Logger log = LoggerFactory.getLogger(ScenarioExecutorImpl.class);

    private final StepExecutionClickCss stepExecutionClickCss;
    private final StepExecutionSleep stepExecutionSleep;
    private final StepExecutionClickXpath stepExecutionClickXpath;
    private final StepExecutionLoggingService stepExecutionLoggingService;
    private final ScenarioResultService scenarioResultService;

    public ScenarioExecutorImpl(StepExecutionClickCss stepExecutionClickCss,
                                StepExecutionSleep stepExecutionSleep,
                                StepExecutionClickXpath stepExecutionClickXpath,
                                StepExecutionLoggingService stepExecutionLoggingService,
                                ScenarioResultService scenarioResultService) {
        this.stepExecutionClickCss = stepExecutionClickCss;
        this.stepExecutionSleep = stepExecutionSleep;
        this.stepExecutionClickXpath = stepExecutionClickXpath;
        this.stepExecutionLoggingService = stepExecutionLoggingService;
        this.scenarioResultService = scenarioResultService;
    }

    @Override
    public void execute(Scenario scenario, WebDriver webDriver) {
        log.info("Execution of scenario: " + scenario.getName() + " is started");
        ScenarioResultResponse scenarioResult = scenarioResultService.createScenarioResult(scenario);
        webDriver.get(scenario.getSite());

        for (Step step : scenario.getSteps()) {
            try {
                String action = step.getAction();
                switch (action) {
                    case "clickCss" -> {
                        stepExecutionClickCss.step(webDriver, step);
                        stepExecutionLoggingService.loggingStep(scenarioResult ,step, true);
                    }
                    case "sleep" -> {
                        stepExecutionSleep.step(webDriver, step);
                        stepExecutionLoggingService.loggingStep(scenarioResult, step, true);
                    }
                    case "clickXpath" -> {
                        stepExecutionClickXpath.step(webDriver, step);
                        stepExecutionLoggingService.loggingStep(scenarioResult, step, true);
                    }
                    default -> {
                        log.error("Invalid step action: " + action);
                        stepExecutionLoggingService.loggingStep(scenarioResult, step, false);
                    }
                }
            } catch(NoSuchElementException | StepExecutionException e) {
                log.error("Scenario: " + scenario.getName() + " - step failed: " + step);
            }
        }

        webDriver.close();
        log.info("Execution of scenario: " + scenario.getName() + " is finished");
    }
}
