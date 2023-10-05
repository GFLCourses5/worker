package executor.service.service.impl.stepExecution;

import executor.service.model.Step;
import executor.service.model.StepTypes;
import executor.service.service.StepExecutionSleep;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code StepExecutionSleepImpl} class implements the {@link StepExecutionSleep} interface
 * for executing a step that involves pausing the execution for a specified duration.
 * <p>
 *
 * @author Anton Sokolsky
 * @version 01
 * @see executor.service.model.StepTypes
 */
public class StepExecutionSleepImpl implements StepExecutionSleep {

    private static final Logger log = LoggerFactory.getLogger(StepExecutionSleepImpl.class);

    @Override
    public String getStepAction() {
        return StepTypes.SLEEP.getName();
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String value = step.getValue();
        try {
            Thread.sleep(Long.parseLong(value) * 1000);
        } catch (InterruptedException e) {
            log.error("Invalid step value: " + value);
        }
    }
}
