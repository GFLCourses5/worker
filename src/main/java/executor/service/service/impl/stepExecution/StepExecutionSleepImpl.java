package executor.service.service.impl.stepExecution;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.Step;
import executor.service.service.StepExecutionSleep;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * The {@code StepExecutionSleepImpl} class implements the {@link StepExecutionSleep} interface
 * for executing a step that involves pausing the execution for a specified duration.
 * <p>
 *
 * @author Anton Sokolsky
 * @version 01
 * @see WebDriver
 * @see Step
 * @see TimeUnit
 * @see InterruptedException
 * @see StepExecutionException
 */
@Service
public class StepExecutionSleepImpl implements StepExecutionSleep {

    private static final Logger log = LoggerFactory.getLogger(StepExecutionSleepImpl.class);

    @Override
    public String getStepAction() {
        return "sleep";
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String value = step.getValue();
        try {
            TimeUnit.SECONDS.sleep(Long.parseLong(value));
        } catch (InterruptedException e) {
            log.error("Thread " + Thread.currentThread().getName() + " was interrupted in StepExecutionSleepImpl.class");
            throw new StepExecutionException(e);
        }
    }
}
