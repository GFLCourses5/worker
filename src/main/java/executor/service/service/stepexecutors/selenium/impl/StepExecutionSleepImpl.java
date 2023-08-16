package executor.service.service.stepexecutors.selenium.impl;

import executor.service.model.entity.Step;
import executor.service.service.stepexecutors.selenium.StepExecutionSleep;
import org.openqa.selenium.WebDriver;

public class StepExecutionSleepImpl implements StepExecutionSleep {
    @Override
    public String getStepAction() {
        return null;
    }

    @Override
    public void step(WebDriver webDriver, Step step) {

    }
}
