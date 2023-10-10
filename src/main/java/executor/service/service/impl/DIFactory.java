package executor.service.service.impl;

import executor.service.config.bean.ThreadPoolExecutor;
import executor.service.config.bean.WebDriverConfigObject;
import executor.service.config.properties.PropertiesConfig;
import executor.service.model.WebDriverConfig;
import executor.service.service.*;
import executor.service.service.impl.listener.*;
import executor.service.service.impl.parallel.*;
import executor.service.service.impl.stepExecution.StepExecutionClickCssImpl;
import executor.service.service.impl.stepExecution.StepExecutionClickXpathImpl;
import executor.service.service.impl.stepExecution.StepExecutionSleepImpl;
import executor.service.service.impl.webDriver.WebDriverInitializerImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Factory for creating objects with dependency injection.
 * This class is intended for creating objects of various classes while considering their dependencies.
 * It utilizes caching to avoid creating the same objects multiple times.
 * Example usage:
 * <pre>
 * DIFactory factory = new DIFactory();
 * ParallelFlowExecutorService parallelExecutor = factory.createObject(ParallelFlowExecutorService.class);
 * // Now you have an instance of ParallelFlowExecutorService with properly configured dependencies.
 * </pre>
 *
 * @author Dima Silenko, Oleksandr Tuleninov.
 * @version 01
 */
public class DIFactory implements ObjectFactory {

    private static DIFactory instance;

    private final Map<Class<?>, Object> cache = new HashMap<>();

    public DIFactory() {
        if (instance != null) {
            throw new IllegalStateException("Singleton instance already exists. Use getInstance() method.");
        }
    }

    public static DIFactory getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (DIFactory.class) {
            if (instance == null) {
                instance = new DIFactory();
            }
        }
        return instance;
    }

    @Override
    public <T> T createObject(Class<T> clazz) {
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        ProxyProvider proxyProvider = new JSONFileProxyProvider();
        WebDriverConfig webDriverConfig = new WebDriverConfigObject(propertiesConfig).webDriverConfig();
        StepExecutionClickCss stepExecutionClickCss = new StepExecutionClickCssImpl();
        StepExecutionSleep stepExecutionSleep = new StepExecutionSleepImpl();
        StepExecutionClickXpath stepExecutionClickXpath = new StepExecutionClickXpathImpl();
        ScenarioExecutor scenarioExecutor = new ScenarioExecutorImpl(stepExecutionClickCss,stepExecutionSleep,stepExecutionClickXpath);
        ScenarioProvider scenarioProvider = new JSONFileScenarioProvider();
        ScenarioSourceListener scenarioSourceListener = new ScenarioSourceListenerImpl(scenarioProvider, propertiesConfig);
        ProxyValidator proxyValidator = new ProxyValidatorImpl(propertiesConfig);
        ProxySourceClient proxySourceClient = new ProxySourceClientImpl(proxyProvider, proxyValidator, propertiesConfig);
        ScenarioSourceQueue scenarioSourceQueue = new ScenarioSourceQueue();
        ScenarioTaskWorker scenarioTaskWorker = new ScenarioTaskWorker(scenarioSourceListener, scenarioSourceQueue);
        ProxySourceQueue proxySourceQueue = new ProxySourceQueue();
        ProxyTaskWorker proxyTaskWorker = new ProxyTaskWorker(proxySourceClient, proxySourceQueue);
        WebDriverInitializer webDriverInitializer = new WebDriverInitializerImpl(propertiesConfig);
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(propertiesConfig).threadPoolExecutor();
        ExecutionService executionService = new ExecutionServiceImpl(scenarioExecutor, webDriverConfig, webDriverInitializer);
        TasksFactory tasksFactory = new TasksFactoryImpl(scenarioTaskWorker, proxyTaskWorker);

        if (ParallelFlowExecutorService.class.isAssignableFrom(clazz)) {
            Object object = cache.get(clazz);

            if (object == null) {
                ParallelFlowExecutorService parallel = new ParallelFlowExecutorServiceImpl(
                        threadPoolExecutor, executionService, tasksFactory);

                cache.put(clazz, parallel);
                return clazz.cast(parallel);
            }
            return clazz.cast(object);
        }
        return null;
    }
}
