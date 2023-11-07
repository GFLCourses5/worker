package executor.service.service.impl.parallel;

import executor.service.service.ExecutionService;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.TasksFactory;
import executor.service.service.impl.proxy.ProxySourceQueueHandler;
import executor.service.service.impl.scenario.ScenarioSourceQueueHandler;
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

    private final ExecutorService threadPoolExecutor;
    private final ScenarioSourceQueueHandler scenarioHandler;
    private final ProxySourceQueueHandler proxyHandler;
    private final ExecutionService service;

    public ParallelFlowExecutorServiceImpl(ExecutorService threadPoolExecutor,
                                           ScenarioSourceQueueHandler scenarioHandler,
                                           ProxySourceQueueHandler proxyHandler,
                                           ExecutionService service) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.service = service;
        this.scenarioHandler = scenarioHandler;
        this.proxyHandler = proxyHandler;
    }

    /**
     * Starts ExecutionService in parallel multithreading mode.
     */
    @Override
    public void execute() {

        threadPoolExecutor.execute(this::runParallelExecute);

    }

    private void runParallelExecute() {
        while (!threadPoolExecutor.isShutdown()){
            threadPoolExecutor.execute(() -> service.execute(
                    scenarioHandler.getScenario(),
                    proxyHandler.getProxy()
            ));
        }
    }


    /**
     * Initiates an orderly shutdown in which previously submitted tasks are executed,
     * but no new tasks will be accepted.
     */
    @Override
    public void shutdown() {
        threadPoolExecutor.shutdown();
    }
}
