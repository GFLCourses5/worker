package executor.service.service.parallel;

import executor.service.config.bean.BeanConfig;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.*;
import executor.service.service.impl.ExecutionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Start ExecutionService in parallel multi-threaded mode.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {

    private static final Logger log = LoggerFactory.getLogger(ParallelFlowExecutorServiceImpl.class);

    private static final BlockingQueue<Scenario> SCENARIO_QUEUE = new LinkedBlockingDeque<>();
    private static final BlockingQueue<ProxyConfigHolder> PROXY_QUEUE = new LinkedBlockingDeque<>();
    private static boolean FLAG = true;

    private ExecutionServiceImpl service;
    private ScenarioSourceListener scenarioSourceListener;
    private ProxySourcesClient proxySourcesClient;
    private BeanConfig beanConfig;
    private ExecutorService threadPoolExecutor;
    private ThreadFactory threadFactory;
    private ProxyValidator proxyValidator;

    public ParallelFlowExecutorServiceImpl() {
    }

    public ParallelFlowExecutorServiceImpl(ExecutionServiceImpl service,
                                           ScenarioSourceListener scenarioSourceListener,
                                           ProxySourcesClient proxySourcesClient,
                                           BeanConfig beanConfig,
                                           ThreadFactory threadFactory,
                                           ProxyValidator proxyValidator) {
        this.service = service;
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
        this.beanConfig = beanConfig;
        this.threadFactory = threadFactory;
        this.proxyValidator = proxyValidator;
    }

    /**
     * Start ScenarioSourceListener, ProxySourcesClient, ExecutionService
     * in parallel multi-threaded mode.
     */
    @Override
    public void execute() {
        threadPoolExecutor = beanConfig.threadPoolExecutor();

        //threadPoolExecutor.execute(threadFactory.createTaskWorker(scenarioSourceListener, SCENARIO_QUEUE));

        //threadPoolExecutor.execute(threadFactory.createTaskWorker(proxySourcesClient, PROXY_QUEUE));

        threadPoolExecutor.execute(new TaskScenario(scenarioSourceListener, SCENARIO_QUEUE));

        threadPoolExecutor.execute(new TaskProxy(proxySourcesClient, PROXY_QUEUE));

        executeScenarioAndProxy();
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

    private void executeScenarioAndProxy() {
        try {
            executeParallel();
        } catch (InterruptedException e) {
            log.error("Thread was interrupted in ParallelFlowExecutorServiceImpl.class", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Execute the scenario and proxy in parallel mode.
     */
    private void executeParallel() throws InterruptedException {
        Scenario scenario;
        ProxyConfigHolder defaultProxy = beanConfig.proxyConfigHolderDefault();
        ProxyConfigHolder proxy;
        while (FLAG) {
            scenario = SCENARIO_QUEUE.take();
            proxy = PROXY_QUEUE.poll();
            if (proxy != null && proxyValidator.isValid(proxy)) defaultProxy = proxy;
            threadPoolExecutor.execute(threadFactory.createExecutionWorker(service, scenario, defaultProxy));
        }
    }
}
