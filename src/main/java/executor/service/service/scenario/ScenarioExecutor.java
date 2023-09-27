package executor.service.service.scenario;

import executor.service.model.scenario.Scenario;
import org.openqa.selenium.WebDriver;

public interface ScenarioExecutor {

    void execute(Scenario scenario, WebDriver webDriver);

}
