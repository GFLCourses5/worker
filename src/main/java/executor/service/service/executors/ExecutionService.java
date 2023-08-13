package executor.service.service.executors;

import executor.service.service.webdrivers.WebDriverInitializer;
import executor.service.service.scenario.ScenarioExecutor;
import executor.service.service.scenario.ScenarioSourceListener;

public class ExecutionService {

    private WebDriverInitializer driverInitializer;
    private ScenarioExecutor scenarioExecutor;
    private ScenarioSourceListener scenarioSourceListener;

    public ExecutionService(WebDriverInitializer driverInitializer, ScenarioExecutor scenarioExecutor, ScenarioSourceListener scenarioSourceListener) {
        this.driverInitializer = driverInitializer;
        this.scenarioExecutor = scenarioExecutor;
        this.scenarioSourceListener = scenarioSourceListener;
    }

    public void execute() {

        scenarioSourceListener.execute();

    }
}
