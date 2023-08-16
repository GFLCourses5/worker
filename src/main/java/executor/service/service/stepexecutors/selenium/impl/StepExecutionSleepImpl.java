package executor.service.service.stepexecutors.selenium.impl;

import executor.service.model.entity.Step;
import executor.service.service.stepexecutors.selenium.SeleniumStepExecutionFactory.SeleniumAction;
import executor.service.service.stepexecutors.selenium.StepExecutionSleep;
import java.util.Random;
import org.openqa.selenium.WebDriver;

public class StepExecutionSleepImpl implements StepExecutionSleep {

    @Override
    public String getStepAction() {
        return SeleniumAction.SLEEP.getName();
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        try {
            Thread.sleep(getRandom(step.getValue()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private int getRandom(String value) {

        String values[] = value.split(":");
        Random random = new Random();
        int min = Integer.parseInt(values[0]);
        int max = Integer.parseInt(values[values.length - 1]);

        return random.ints(min, max + 1)
            .findFirst().orElse(1);

    }


}
