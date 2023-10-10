package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.TasksFactory;

import java.util.concurrent.Callable;

/**
 * The {@code TasksFactoryImpl} class is an implementation
 * of the {@link TasksFactory} interface.
 * It provides methods for creating task and execution workers.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ScenarioTaskWorker
 * @see ProxyTaskWorker
 * @see ExecutionService
 * @see Scenario
 * @see ProxyConfigHolder
 */
public class TasksFactoryImpl implements TasksFactory {

    private final ScenarioTaskWorker scenarioTaskWorker;
    private final ProxyTaskWorker proxyTaskWorker;

    public TasksFactoryImpl(ScenarioTaskWorker scenarioTaskWorker,
                            ProxyTaskWorker proxyTaskWorker) {
        this.scenarioTaskWorker = scenarioTaskWorker;
        this.proxyTaskWorker = proxyTaskWorker;
    }

    @Override
    public Callable<Scenario> createScenarioTaskWorker() {
        return scenarioTaskWorker;
    }

    @Override
    public Callable<ProxyConfigHolder> createProxyTaskWorker() {
        return proxyTaskWorker;
    }

    @Override
    public Runnable createExecutionWorker(ExecutionService service,
                                          Scenario scenario,
                                          ProxyConfigHolder proxy) {
        return new ExecutionWorker(service, scenario, proxy);
    }
}
