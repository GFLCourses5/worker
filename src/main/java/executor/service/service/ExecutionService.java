package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.request.Scenario;

/**
 * The facade for execute ScenarioExecutor.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see Scenario
 * @see ProxyConfigHolder
 */
public interface ExecutionService {

    void execute(Scenario scenario, ProxyConfigHolder proxy);

}
