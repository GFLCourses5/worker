package executor.service.service.stepexecutors;

import executor.service.model.entity.Step;
import org.openqa.selenium.WebDriver;

public interface StepExecution {

  String getStepAction();

  void step(WebDriver webDriver, Step step);

}
