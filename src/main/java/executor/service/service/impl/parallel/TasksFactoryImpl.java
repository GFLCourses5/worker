package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.request.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.TasksFactory;
import org.springframework.stereotype.Service;

/**
 * The {@code TasksFactoryImpl} class is an implementation
 * of the {@link TasksFactory} interface.
 * It provides methods for creating task and execution workers.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ExecutionWorker
 * @see ExecutionService
 * @see Scenario
 * @see ProxyConfigHolder
 * @see Runnable
 */
@Service
public class TasksFactoryImpl implements TasksFactory {

    @Override
    public Runnable createExecutionWorker(ExecutionService service,
                                          Scenario scenario,
                                          ProxyConfigHolder proxy) {
        return new ExecutionWorker(service, scenario, proxy);
    }
}
