package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.parallel.ItemQueue;

import java.util.concurrent.Callable;

/**
 * Callable, Runnable Factory.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface TasksFactory<T> {

    Callable<ItemQueue<T>> createTaskWorker(Listener listener);

    Runnable createExecutionWorker(ExecutionService service,
                                   Scenario scenario,
                                   ProxyConfigHolder proxy);

}
