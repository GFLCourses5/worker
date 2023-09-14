package executor.service.service.executionservice;

import executor.service.service.scenario.ScenarioExecutor;
import executor.service.service.sources.ScenarioSourceListener;
import executor.service.service.webdriver.WebDriverInitializer;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;

/**
 * ExecutionService facade.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface ExecutionService {

    void execute(Scenario scenario, ProxyConfigHolder proxy);
}
