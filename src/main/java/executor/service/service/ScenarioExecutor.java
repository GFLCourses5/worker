package executor.service.service;

import executor.service.model.request.Scenario;
import org.openqa.selenium.WebDriver;

public interface ScenarioExecutor {

    void execute(Scenario scenario, WebDriver webDriver);

}
