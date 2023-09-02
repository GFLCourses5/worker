package executor.service.service.impl;

import executor.service.model.Step;
import executor.service.model.enums.StepAction;
import executor.service.service.StepExecution;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StepExecutionSleep implements StepExecution {

    private static final Logger log = LoggerFactory.getLogger(StepExecution.class);

    @Override
    public String getStepAction() {
        return StepAction.SLEEP.getName();
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String sleepDurationString = step.getValue();
        log.info("Executing {} {}", step.getAction(), step.getValue());
        try {
            long sleepDurationMillis = parseSleepDuration(sleepDurationString);
            Thread.sleep(sleepDurationMillis);
        } catch (InterruptedException e) {
            log.error("Error interrupting thread sleep.", e);
        } catch (NumberFormatException e) {
            log.error("Wrong sleep value format.", e);
        }
    }

    private long parseSleepDuration(String sleepDurationString) {
        String[] parts = sleepDurationString.split(":");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid sleep duration format: " + sleepDurationString);
        }

        int seconds = Integer.parseInt(parts[0]);
        int milliseconds = Integer.parseInt(parts[1]);

        return (seconds * 1000 + milliseconds);
    }
}
