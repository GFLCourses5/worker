package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * The {@code ParallelFlowExecutorServiceImpl} class implements the {@link ParallelFlowExecutorService} interface
 * to execute ScenarioSourceListener, ProxySourcesClient, and ExecutionService in parallel multithreading mode.
 * It manages the execution of these services by utilizing an {@link ExecutorService} and {@link TasksFactory}.
 * This class also provides a method to gracefully shut down the executor service.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see executor.service.service.ExecutionService
 * @see executor.service.service.ScenarioSourceListener
 * @see executor.service.service.ProxySourcesClient
 */
public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {

    private static final Logger log = LoggerFactory.getLogger(ParallelFlowExecutorServiceImpl.class);
    private static boolean FLAG = true;

    private final ExecutorService threadPoolExecutor;
    private final ExecutionService service;
    private final ScenarioSourceListener scenarioSourceListener;
    private final ProxySourcesClient proxySourcesClient;
    private final TasksFactory tasksFactory;

    public ParallelFlowExecutorServiceImpl(ExecutorService threadPoolExecutor,
                                           ExecutionService service,
                                           ScenarioSourceListener scenarioSourceListener,
                                           ProxySourcesClient proxySourcesClient,
                                           TasksFactory tasksFactory) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.service = service;
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
        this.tasksFactory = tasksFactory;
    }

    /**
     * Starts the ScenarioSourceListener, ProxySourcesClient, and ExecutionService
     * in parallel multithreading mode.
     */
    @Override
    public void execute() {
        Future<ItemQueue> futureScenarios
                = threadPoolExecutor.submit(tasksFactory.createTaskWorker(scenarioSourceListener));

        Future<ItemQueue> futureProxies
                = threadPoolExecutor.submit(tasksFactory.createTaskWorker(proxySourcesClient));

        executeScenarioAndProxy(futureScenarios, futureProxies);
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

    private void executeScenarioAndProxy(Future<ItemQueue> futureScenarios,
                                         Future<ItemQueue> futureProxies) {
        try {
            executeParallel(futureScenarios, futureProxies);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Thread was interrupted in ParallelFlowExecutorServiceImpl.class", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Execute the scenario and proxy in parallel mode.
     *
     * @param futureScenarios queue with scenarios
     * @param futureProxies   queue with proxies
     */
    private void executeParallel(Future<ItemQueue> futureScenarios,
                                 Future<ItemQueue> futureProxies)
            throws InterruptedException, ExecutionException {
        ItemQueue scenarios = futureScenarios.get();
        ItemQueue proxies = futureProxies.get();
        Scenario scenario;
        ProxyConfigHolder proxy;
        while (FLAG) {
            scenario = (Scenario) scenarios.getItem();
            proxy = (ProxyConfigHolder) proxies.getItem();
            threadPoolExecutor.execute(tasksFactory.createExecutionWorker(service, scenario, proxy));
        }
    }
}
