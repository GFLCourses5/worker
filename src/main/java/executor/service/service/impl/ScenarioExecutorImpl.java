package executor.service.service.impl;

import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.service.ScenarioExecutor;
import executor.service.service.StepExecution;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ScenarioExecutorImpl implements ScenarioExecutor {
    @Override
    public void execute(Scenario scenario, WebDriver webDriver) {
        webDriver.get("url");
        List<Step> steps = scenario.getSteps();
        for (Step step: steps) {

            StepExecution executor = getStepExecutor(step.getAction());
            executor.step(webDriver, step);

        }
        webDriver.close();
    }

    private StepExecution getStepExecutor(String action) {
        StepExecution executor = null;
        switch (action) {
            case "clickCss":
                executor = new StepExecutionClickCss();
                break;
            case "clickXpath":
                executor = new StepExecutionClickXpath();
                break;
            case "sleep":
                executor = new StepExecutionSleep();
                break;
            default:
                throw new IllegalArgumentException("Invalid step type: " + action);
        }
        return executor;
    }

}
