package executor.service.service.impl;

import executor.service.config.bean.BeanConfig;
import executor.service.config.properties.PropertiesConfig;
import executor.service.model.WebDriverConfig;
import executor.service.service.*;
import executor.service.service.impl.listener.*;
import executor.service.service.impl.parallel.ParallelFlowExecutorServiceImpl;
import executor.service.service.impl.parallel.TasksFactoryImpl;
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

    private final Map<Class<?>, Object> cache = new HashMap<>();
    private final ExecutorService threadPoolExecutor;
    private final ExecutionService executionService;
    private final ScenarioSourceListener scenarioSourceListener;
    private final ProxySourcesClient proxySourcesClient;
    private final TasksFactory tasksFactory;

    public DIFactory() {
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        ProxyProvider proxyProvider = new JSONFileProxyProvider();
        BeanConfig beanConfig = new BeanConfig(propertiesConfig, proxyProvider);
        this.threadPoolExecutor = beanConfig.threadPoolExecutor();
        WebDriverConfig webDriverConfig = beanConfig.webDriverConfig();
        StepExecutionClickCss stepExecutionClickCss = new StepExecutionClickCssImpl();
        StepExecutionSleep stepExecutionSleep = new StepExecutionSleepImpl();
        StepExecutionClickXpath stepExecutionClickXpath = new StepExecutionClickXpathImpl();
        ScenarioExecutor scenarioExecutor = new ScenarioExecutorImpl(
                stepExecutionClickCss,
                stepExecutionSleep,
                stepExecutionClickXpath);
        this.executionService = new ExecutionServiceImpl(scenarioExecutor, webDriverConfig, new WebDriverInitializerImpl(propertiesConfig));
        ScenarioProvider scenarioProvider = new JSONFileScenarioProvider();
        this.scenarioSourceListener = new ScenarioSourceListenerImpl(scenarioProvider, propertiesConfig);
        ProxyValidator proxyValidator = new ProxyValidatorImpl(propertiesConfig);
        this.proxySourcesClient = new ProxySourcesClientImpl(proxyProvider, proxyValidator, propertiesConfig);
        this.tasksFactory = new TasksFactoryImpl();
    }

    @Override
    public <T> T createObject(Class<T> clazz) {
        if (ParallelFlowExecutorService.class.isAssignableFrom(clazz)) {
            Object object = cache.get(clazz);

            if (object == null) {
                ParallelFlowExecutorService parallel = new ParallelFlowExecutorServiceImpl(
                        threadPoolExecutor, executionService, scenarioSourceListener, proxySourcesClient, tasksFactory);

                cache.put(clazz, parallel);
                return clazz.cast(parallel);
            }
            return clazz.cast(object);
        }
        return null;
    }
}
