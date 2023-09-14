package executor.service.service.executionservices;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;


/**
 * ExecutionService facade.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface ExecutionService {

    void execute(Scenario scenario, ProxyConfigHolder proxy);
}
