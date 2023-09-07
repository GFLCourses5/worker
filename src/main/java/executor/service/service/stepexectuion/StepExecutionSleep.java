package executor.service.service.stepexectuion;

import executor.service.model.scenario.Step;
import org.openqa.selenium.WebDriver;

public interface StepExecutionSleep {

    String getStepAction();

    void step(WebDriver webDriver, Step step);

}
