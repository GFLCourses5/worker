package executor.service.service.stepexecutors.selenium.impl;

import executor.service.model.entity.Step;
import executor.service.service.stepexecutors.selenium.SeleniumStepExecutionFactory.SeleniumAction;
import executor.service.service.stepexecutors.selenium.StepExecutionSleep;
import org.openqa.selenium.WebDriver;

public class StepExecutionSleepImpl implements StepExecutionSleep {

    @Override
    public String getStepAction() {
        return SeleniumAction.SLEEP.getName();
    }

    @Override
    public void step(WebDriver webDriver, Step step) {

        Range range = getRange(step.getValue());

        try {
            Thread.sleep(range.random());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    static class Range {

        int min;
        int max;

        Range(int min, int max) {
            this.max = max;
            this.min = min;
        }

        int random() {
            max -= min;
            return (int) (Math.random() * ++max) + min;
        }
    }

    private Range getRange(String value) {
        String[] rangeValue = value.split(";");

        int min = Integer.parseInt(rangeValue[0]);
        int max = Integer.parseInt(rangeValue[rangeValue.length - 1]);
        return new Range(min, max);
    }
}
