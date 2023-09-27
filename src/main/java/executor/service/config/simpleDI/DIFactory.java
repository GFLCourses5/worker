package executor.service.config.simpleDI;


import executor.service.config.bean.BeanConfig;
import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.WebDriverConfig;
import executor.service.service.*;
import executor.service.service.impl.*;
import executor.service.service.parallel.ParallelFlowExecutorServiceImpl;
import executor.service.service.parallel.TasksFactoryImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class DIFactory implements ObjectFactory {

    private static final Map<Class<?>, Object> cache = new HashMap<>();

    @Override
    public <T> T createObject(Class<T> clazz) {

        if (ParallelFlowExecutorService.class.isAssignableFrom(clazz)) {
            Object object = cache.get(clazz);

            if (object == null) {
                PropertiesConfig propertiesConfig = new PropertiesConfig();
                Provider provider = new JSONMapper();
                BeanConfig beanConfig = new BeanConfig(propertiesConfig, provider);
                ExecutorService threadPoolExecutor = beanConfig.threadPoolExecutor();
                WebDriverConfig webDriverConfig = beanConfig.webDriverConfig();
                ProxyConfigHolder defaultProxy = beanConfig.proxyConfigHolderDefault();
                StepExecutionClickCss stepExecutionClickCss = new StepExecutionClickCssImpl();
                StepExecutionSleep stepExecutionSleep = new StepExecutionSleepImpl();
                StepExecutionClickXpath stepExecutionClickXpath = new StepExecutionClickXpathImpl();
                ScenarioExecutor scenarioExecutor = new ScenarioExecutorImpl(
                        stepExecutionClickCss,
                        stepExecutionSleep,
                        stepExecutionClickXpath);
                ExecutionService executionService = new ExecutionServiceImpl(scenarioExecutor, webDriverConfig);
                ScenarioProvider scenarioProvider = new JSONFileScenarioProvider();
                ScenarioSourceListener scenarioSourceListener = new ScenarioSourceListenerImpl(scenarioProvider);
                ProxyValidator proxyValidator = new ProxyValidatorImpl();
                ProxySourcesClient proxySourcesClient = new ProxySourcesClientImpl(provider, proxyValidator);
                TasksFactory tasksFactory = new TasksFactoryImpl();
                ParallelFlowExecutorService parallel = new ParallelFlowExecutorServiceImpl(
                        threadPoolExecutor, executionService, scenarioSourceListener, proxySourcesClient,
                        tasksFactory, defaultProxy, proxyValidator);

                Object[] objectsToCache = {
                        propertiesConfig, provider, beanConfig, threadPoolExecutor, webDriverConfig, defaultProxy,
                        stepExecutionClickCss, stepExecutionSleep, stepExecutionClickXpath, scenarioExecutor,
                        executionService, scenarioProvider, scenarioSourceListener, proxyValidator,
                        proxySourcesClient, tasksFactory, parallel
                };

                for (Object obj : objectsToCache) {
                    cache.put(obj.getClass(), obj);
                }

                return clazz.cast(parallel);
            }
            return clazz.cast(object);
        }
        return null;
    }
}
