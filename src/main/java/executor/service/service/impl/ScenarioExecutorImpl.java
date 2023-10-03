package executor.service.service.impl;

import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.service.ScenarioExecutor;
import executor.service.service.StepExecutionClickCss;
import executor.service.service.StepExecutionClickXpath;
import executor.service.service.StepExecutionSleep;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Selects a StepExecution for each Step and runs it..
 *
 *  @author Oleksandr Tuleninov
 *  @version 01
 * */
public class ScenarioExecutorImpl implements ScenarioExecutor {

    private static final Logger log = LoggerFactory.getLogger(ScenarioExecutorImpl.class);

    private final StepExecutionClickCss stepExecutionClickCss;
    private final StepExecutionSleep stepExecutionSleep;
    private final StepExecutionClickXpath stepExecutionClickXpath;

    public ScenarioExecutorImpl(StepExecutionClickCss stepExecutionClickCss,
                                StepExecutionSleep stepExecutionSleep,
                                StepExecutionClickXpath stepExecutionClickXpath) {
        this.stepExecutionClickCss = stepExecutionClickCss;
        this.stepExecutionSleep = stepExecutionSleep;
        this.stepExecutionClickXpath = stepExecutionClickXpath;
    }

    @Override
    public void execute(Scenario scenario, WebDriver webDriver) {
        webDriver.get(scenario.getSite());

        for (Step step : scenario.getSteps()) {
            String action = step.getAction().getName();
            switch (action) {
                case "clickCss" -> stepExecutionClickCss.step(webDriver, step);
                case "sleep" -> stepExecutionClickXpath.step(webDriver, step);
                case "clickXpath" -> stepExecutionSleep.step(webDriver, step);
                default -> log.error("Invalid step action: " + action);
            }
        }
        log.info("Scenario: " + scenario.getName() + " is finished.");

        webDriver.close();
    }
}
