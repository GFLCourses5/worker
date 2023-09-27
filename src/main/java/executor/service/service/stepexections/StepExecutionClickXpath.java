package executor.service.service.stepexections;

import executor.service.model.scenario.Step;
import org.openqa.selenium.WebDriver;

public interface StepExecutionClickXpath {

    String getStepAction();

    void step(WebDriver webDriver, Step step);

}
