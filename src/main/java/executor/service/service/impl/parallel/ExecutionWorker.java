package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.request.Scenario;
import executor.service.service.ExecutionService;

/**
 * The {@code ExecutionWorker} class implements the {@link Runnable} interface
 * and is responsible for executing a scenario using an {@link ExecutionService}
 * with a specified scenario and proxy configuration.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see Scenario
 * @see ProxyConfigHolder
 */
public class ExecutionWorker implements Runnable {

    private final ExecutionService service;
    private final Scenario scenario;
    private final ProxyConfigHolder proxy;

    public ExecutionWorker(ExecutionService service,
                           Scenario scenario,
                           ProxyConfigHolder proxy) {
        this.service = service;
        this.scenario = scenario;
        this.proxy = proxy;
    }

    @Override
    public void run() {
        service.execute(scenario, proxy);
    }
}
