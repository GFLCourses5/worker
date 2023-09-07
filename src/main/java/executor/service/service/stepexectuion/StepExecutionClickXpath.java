package executor.service.service.stepexectuion;

import executor.service.model.scenario.Step;
import org.openqa.selenium.WebDriver;

public interface StepExecutionClickXpath {

    String getStepAction();

    void step(WebDriver webDriver, Step step);

}
