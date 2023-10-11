package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;

import java.util.concurrent.Callable;

/**
 * The {@code TasksFactory} interface defines methods
 * for creating task and execution workers.
 * Task workers are responsible for reading data from json files,
 * execution workers handle the execution
 * of specific tasks.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ExecutionService
 * @see Scenario
 * @see ProxyConfigHolder
 */
public interface TasksFactory {

    Callable<Scenario> createScenarioTaskWorker();

    Callable<ProxyConfigHolder> createProxyTaskWorker();

    Runnable createExecutionWorker(ExecutionService service,
                                   Scenario scenario,
                                   ProxyConfigHolder proxy);

}
