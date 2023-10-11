package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.ProxySourceClient;
import executor.service.service.TasksFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * The {@code ParallelFlowExecutorServiceImpl} class implements the {@link ParallelFlowExecutorService} interface
 * to execute ExecutionService in parallel multithreading mode.
 * It manages the execution of these services by utilizing an {@link ExecutorService} and {@link TasksFactory}.
 * This class also provides a method to gracefully shut down the executor service.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ExecutorService
 * @see ExecutionService
 * @see TasksFactory
 * @see Scenario
 * @see ProxySourceClient
 */
public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {

    private static boolean FLAG = true;

    private final ExecutorService threadPoolExecutor;
    private final ExecutionService service;
    private final TasksFactory tasksFactory;

    public ParallelFlowExecutorServiceImpl(ExecutorService threadPoolExecutor,
                                           ExecutionService service,
                                           TasksFactory tasksFactory) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.service = service;
        this.tasksFactory = tasksFactory;
    }

    /**
     * Starts the getting scenario, getting proxy and ExecutionService
     * in parallel multithreading mode.
     */
    @Override
    public void execute() {
        while (FLAG) {
            Scenario scenario = getScenario(threadPoolExecutor.submit(tasksFactory.createScenarioTaskWorker()));

            ProxyConfigHolder proxy = getProxy(threadPoolExecutor.submit(tasksFactory.createProxyTaskWorker()));

            threadPoolExecutor.execute(tasksFactory.createExecutionWorker(service, scenario, proxy));
        }
    }

    private Scenario getScenario(Future<Scenario> futureScenarios) {
        Scenario scenario;
        try {
            scenario = futureScenarios.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return scenario;
    }

    private ProxyConfigHolder getProxy(Future<ProxyConfigHolder> futureProxies) {
        ProxyConfigHolder proxy;
        try {
            proxy = futureProxies.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return proxy;
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
