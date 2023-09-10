package executor.service.service.parallel;

import executor.service.config.bean.BeanConfig;
import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.ThreadPoolConfig;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ProxyValidator;
import executor.service.service.ScenarioSourceListener;
import executor.service.service.ThreadFactory;
import executor.service.service.impl.ExecutionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

import static executor.service.config.properties.PropertiesConstants.*;

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
    private PropertiesConfig propertiesConfig;
    private ThreadPoolConfig threadPoolConfig;
    private ExecutorService threadPoolExecutor;
    private ThreadFactory threadFactory;
    private BeanConfig beanConfig;
    private ProxyValidator proxyValidator;

    public ParallelFlowExecutorServiceImpl() {
    }

    public ParallelFlowExecutorServiceImpl(ExecutionServiceImpl service,
                                           ScenarioSourceListener scenarioSourceListener,
                                           ProxySourcesClient proxySourcesClient,
                                           PropertiesConfig propertiesConfig,
                                           ThreadPoolConfig threadPoolConfig,
                                           ThreadFactory threadFactory,
                                           BeanConfig beanConfig,
                                           ProxyValidator proxyValidator) {
        this.service = service;
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
        this.propertiesConfig = propertiesConfig;
        this.threadPoolConfig = threadPoolConfig;
        this.threadFactory = threadFactory;
        this.beanConfig = beanConfig;
        this.proxyValidator = proxyValidator;
    }

    /**
     * Start ScenarioSourceListener, ProxySourcesClient, ExecutionService
     * in parallel multi-threaded mode.
     */
    @Override
    public void execute() {
        configureThreadPoolConfig(propertiesConfig, threadPoolConfig);
        threadPoolExecutor = createThreadPoolExecutor(threadPoolConfig);

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

    /**
     * Create ThreadPoolExecutor.
     *
     * @param threadPoolConfig the config for the ThreadPoolExecutor
     * @return the ThreadPoolExecutor entity
     */
    private ThreadPoolExecutor createThreadPoolExecutor(ThreadPoolConfig threadPoolConfig) {
        return new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                defineMaximumAvailableProcessors(),
                threadPoolConfig.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    /**
     * Configure ThreadPoolConfig from properties file.
     *
     * @param propertiesConfig the properties from resources file
     * @param threadPoolConfig the ThreadPoolConfig entity
     */
    private void configureThreadPoolConfig(PropertiesConfig propertiesConfig, ThreadPoolConfig threadPoolConfig) {
        var properties = propertiesConfig.getProperties(THREAD_POOL_PROPERTIES);
        var corePoolSize = Integer.parseInt(properties.getProperty(CORE_POOL_SIZE));
        var keepAliveTime = Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME));
        threadPoolConfig.setCorePoolSize(corePoolSize);
        threadPoolConfig.setKeepAliveTime(keepAliveTime);
    }

    /**
     * Get the number of available processor cores.
     *
     * @return the number of available processor core
     */
    private int defineMaximumAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }
}
