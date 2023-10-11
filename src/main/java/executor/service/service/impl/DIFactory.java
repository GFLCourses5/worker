package executor.service.service.impl;

import executor.service.config.bean.ThreadPoolExecutorObject;
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

    private final Map<Class<?>, Object> cache = new ConcurrentHashMap<>();

    private DIFactory() {
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
        Object object = cache.get(clazz);

        if (object != null) {
            return (T) object;
        }

        if (clazz == PropertiesConfig.class) {
            object = new PropertiesConfig();
        } else if (clazz == ProxyProvider.class) {
            object = new JSONFileProxyProvider();
        } else if (clazz == WebDriverConfig.class) {
            PropertiesConfig propertiesConfig = createObject(PropertiesConfig.class);
            object = new WebDriverConfigObject(propertiesConfig).webDriverConfig();
        } else if (clazz == StepExecutionClickCss.class) {
            object = new StepExecutionClickCssImpl();
        } else if (clazz == StepExecutionSleep.class) {
            object = new StepExecutionSleepImpl();
        } else if (clazz == StepExecutionClickXpath.class) {
            object = new StepExecutionClickXpathImpl();
        } else if (clazz == ScenarioExecutor.class) {
            StepExecutionClickCss stepExecutionClickCss = createObject(StepExecutionClickCss.class);
            StepExecutionSleep stepExecutionSleep = createObject(StepExecutionSleep.class);
            StepExecutionClickXpath stepExecutionClickXpath = createObject(StepExecutionClickXpath.class);
            object = new ScenarioExecutorImpl(stepExecutionClickCss, stepExecutionSleep, stepExecutionClickXpath);
        } else if (clazz == ScenarioProvider.class) {
            object = new JSONFileScenarioProvider();
        } else if (clazz == ScenarioSourceListener.class) {
            ScenarioProvider scenarioProvider = createObject(ScenarioProvider.class);
            PropertiesConfig propertiesConfig = createObject(PropertiesConfig.class);
            object = new ScenarioSourceListenerImpl(scenarioProvider, propertiesConfig);
        } else if (clazz == ProxyValidator.class) {
            PropertiesConfig propertiesConfig = createObject(PropertiesConfig.class);
            object = new ProxyValidatorImpl(propertiesConfig);
        } else if (clazz == ProxySourceClient.class) {
            ProxyProvider proxyProvider = createObject(ProxyProvider.class);
            ProxyValidator proxyValidator = createObject(ProxyValidator.class);
            PropertiesConfig propertiesConfig = createObject(PropertiesConfig.class);
            object = new ProxySourceClientImpl(proxyProvider, proxyValidator, propertiesConfig);
        } else if (clazz == ScenarioSourceQueue.class) {
            object = new ScenarioSourceQueue();
        } else if (clazz == ScenarioTaskWorker.class) {
            ScenarioSourceListener scenarioSourceListener = createObject(ScenarioSourceListener.class);
            ScenarioSourceQueue scenarioSourceQueue = createObject(ScenarioSourceQueue.class);
            object = new ScenarioTaskWorker(scenarioSourceListener, scenarioSourceQueue);
        } else if (clazz == ProxySourceQueue.class) {
            object = new ProxySourceQueue();
        } else if (clazz == ProxyTaskWorker.class) {
            ProxySourceClient proxySourceClient = createObject(ProxySourceClient.class);
            ProxySourceQueue proxySourceQueue = createObject(ProxySourceQueue.class);
            object = new ProxyTaskWorker(proxySourceClient, proxySourceQueue);
        } else if (clazz == WebDriverInitializer.class) {
            PropertiesConfig propertiesConfig = createObject(PropertiesConfig.class);
            WebDriverConfig webDriverConfig = createObject(WebDriverConfig.class);
            object = new WebDriverInitializerImpl(propertiesConfig, webDriverConfig);
        } else if (clazz == ExecutorService.class) {
            PropertiesConfig propertiesConfig = createObject(PropertiesConfig.class);
            object = new ThreadPoolExecutorObject(propertiesConfig).threadPoolExecutor();
        } else if (clazz == ExecutionService.class) {
            ScenarioExecutor scenarioExecutor = createObject(ScenarioExecutor.class);
            WebDriverConfig webDriverConfig = createObject(WebDriverConfig.class);
            WebDriverInitializer webDriverInitializer = createObject(WebDriverInitializer.class);
            object = new ExecutionServiceImpl(scenarioExecutor, webDriverConfig, webDriverInitializer);
        } else if (clazz == TasksFactory.class) {
            ScenarioTaskWorker scenarioTaskWorker = createObject(ScenarioTaskWorker.class);
            ProxyTaskWorker proxyTaskWorker = createObject(ProxyTaskWorker.class);
            object = new TasksFactoryImpl(scenarioTaskWorker, proxyTaskWorker);
        } else if (clazz == ParallelFlowExecutorService.class) {
            ExecutorService threadPoolExecutor = createObject(ExecutorService.class);
            ExecutionService executionService = createObject(ExecutionService.class);
            TasksFactory tasksFactory = createObject(TasksFactory.class);
            object = new ParallelFlowExecutorServiceImpl(threadPoolExecutor, executionService, tasksFactory);
        }

        if (object != null) {
            cache.put(clazz, object);
            return (T) object;
        }

        return null;
    }
}
