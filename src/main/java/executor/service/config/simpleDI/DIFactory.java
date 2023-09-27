package executor.service.config.simpleDI;


import executor.service.config.bean.BeanConfig;
import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.WebDriverConfig;
import executor.service.service.*;
import executor.service.service.impl.*;
import executor.service.service.parallel.ParallelFlowExecutorServiceImpl;
import executor.service.service.Provider;
import executor.service.service.parallel.TasksFactoryImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                StepExecutionClickCss stepExecutionClickCss = new StepExecutionClickCssImpl();
                StepExecutionSleep stepExecutionSleep = new StepExecutionSleepImpl();
                StepExecutionClickXpath stepExecutionClickXpath = new StepExecutionClickXpathImpl();
                WebDriverConfig webDriverConfig = beanConfig.webDriverConfig();
                ProxyConfigHolder proxyConfigHolder = beanConfig.proxyConfigHolderDefault();
                List<StepExecution> listSteps = getStepExecutions(stepExecutionClickCss, stepExecutionSleep,
                        stepExecutionClickXpath);
                StepExecutionFabric stepExecutionFabric = new StepExecutionFactory(listSteps);
                ExecutionService executionService = new ExecutionServiceImpl(stepExecutionFabric, webDriverConfig);
                ScenarioProvider scenarioProvider = new JSONFileScenarioProvider();
                ScenarioSourceListener scenarioSourceListener  = new ScenarioSourceListenerImpl(scenarioProvider);
                ProxyValidator proxyValidator = new ProxyValidatorImpl();
                ProxyProvider proxyProvider = new JSONFileProxyProvider();
                ProxySourcesClient proxySourcesClient = new ProxySourcesClientImpl(proxyProvider, proxyValidator);
                TasksFactory tasksFactory = new TasksFactoryImpl();
                ParallelFlowExecutorService parallel = new ParallelFlowExecutorServiceImpl(threadPoolExecutor,
                        executionService, scenarioSourceListener, proxySourcesClient, tasksFactory, proxyConfigHolder,
                        proxyValidator);

                Object[] objectsToCache = {
                        propertiesConfig, provider, beanConfig, threadPoolExecutor,
                        stepExecutionFabric, stepExecutionClickCss, stepExecutionSleep,
                        stepExecutionClickXpath, webDriverConfig,
                        proxyConfigHolder, executionService, scenarioProvider,
                        scenarioSourceListener, proxyValidator, proxyProvider,
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

    private static List<StepExecution> getStepExecutions(StepExecutionClickCss stepExecutionClickCss,
                                                         StepExecutionSleep stepExecutionSleep,
                                                         StepExecutionClickXpath stepExecutionClickXpath) {
        List<StepExecution> listSteps = new ArrayList<>();
        listSteps.add(stepExecutionClickCss);
        listSteps.add(stepExecutionSleep);
        listSteps.add(stepExecutionClickXpath);
        return listSteps;
    }
}
