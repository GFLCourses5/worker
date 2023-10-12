package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;

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

    Runnable createExecutionWorker(ExecutionService service,
                                   Scenario scenario,
                                   ProxyConfigHolder proxy);

}
