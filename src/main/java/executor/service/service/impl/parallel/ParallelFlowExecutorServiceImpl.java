package executor.service.service.impl.parallel;

import executor.service.service.ExecutionService;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.TasksFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

/**
 * The {@code ParallelFlowExecutorServiceImpl} class implements the {@link ParallelFlowExecutorService} interface
 * to execute ExecutionService in parallel multithreading mode.
 * It manages the execution of these services by utilizing an {@link ExecutorService} and {@link TasksFactory}.
 * This class also provides a method to gracefully shut down the executor service.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ScenarioSourceQueueHandler
 * @see ProxySourceQueueHandler
 * @see ExecutionService
 */
@Service
public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {

    private static boolean FLAG = true;

    private final ExecutorService threadPoolExecutor;
    private final ScenarioSourceQueueHandler scenarioHandler;
    private final ProxySourceQueueHandler proxyHandler;
    private final TasksFactory tasksFactory;
    private final ExecutionService service;

    public ParallelFlowExecutorServiceImpl(ExecutorService threadPoolExecutor,
                                           ScenarioSourceQueueHandler scenarioHandler,
                                           ProxySourceQueueHandler proxyHandler,
                                           TasksFactory tasksFactory,
                                           ExecutionService service) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.service = service;
        this.tasksFactory = tasksFactory;
        this.scenarioHandler = scenarioHandler;
        this.proxyHandler = proxyHandler;
    }

    /**
     * Starts the getting scenario, getting proxy and ExecutionService
     * in parallel multithreading mode.
     */
    @Override
    public void execute() {
        threadPoolExecutor.execute(scenarioHandler);
        threadPoolExecutor.execute(proxyHandler);

        while (FLAG) {
            threadPoolExecutor.execute(tasksFactory.createExecutionWorker(
                    service, scenarioHandler.getScenario(), proxyHandler.getProxy()));
        }
    }

    /**
     * Initiates an orderly shutdown in which previously submitted tasks are executed,
     * but no new tasks will be accepted.
     */
    @Override
    public void shutdown() {
        FLAG = false;
        threadPoolExecutor.shutdown();
    }
}
