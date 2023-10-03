package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.impl.parallel.ItemQueue;

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
 * @see executor.service.service.Listener
 * @see executor.service.service.ExecutionService
 * @see executor.service.model.Scenario
 * @see executor.service.model.ProxyConfigHolder
 */
public interface TasksFactory {

    Callable<ItemQueue> createTaskWorker(Listener listener);

    Runnable createExecutionWorker(ExecutionService service,
                                   Scenario scenario,
                                   ProxyConfigHolder proxy);

}
