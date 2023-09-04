package executor.service.service;

import executor.service.service.executors.ParallelFlowExecutorService;
import executor.service.service.proxy.selenium.ProxySourcesClientImpl;
import executor.service.service.proxy.ProxySourcesClient;
import executor.service.service.scenario.ScenarioExecutor;
import executor.service.service.scenario.ScenarioSourceListener;
import executor.service.service.scenario.selenium.ScenarioExecutorImpl;
import executor.service.service.scenario.common.ScenarioSourceListenerImpl;
import executor.service.service.stepexecutors.StepExecutionClickCss;
import executor.service.service.stepexecutors.impl.StepExecutionClickCssImpl;
import executor.service.service.stepexecutors.StepExecutionClickXpath;
import executor.service.service.stepexecutors.impl.StepExecutionClickXpathImpl;
import executor.service.service.stepexecutors.StepExecutionSleep;
import executor.service.service.stepexecutors.impl.StepExecutionSleepImpl;
import executor.service.service.webdrivers.WebDriverInitializer;

public class FactoryForDI {
    private final WebDriverInitializer webDriverInitializer;
    private final ProxySourcesClient proxySourcesClient;
    private final ScenarioSourceListener scenarioSourceListener;
    private final ParallelFlowExecutorService parallelFlowExecutorService;
    //private final ExecutionService executionService;
    private final ScenarioExecutor scenarioExecutor;
    private final StepExecutionSleep stepExecutionSleep;
    private final StepExecutionClickXpath stepExecutionClickXpath;

    private final StepExecutionClickCss stepExecutionClickCss;

    public FactoryForDI() {
        webDriverInitializer = new WebDriverInitializer(null,null);
        proxySourcesClient = new ProxySourcesClientImpl();
        scenarioSourceListener = new ScenarioSourceListenerImpl(null);
        parallelFlowExecutorService = new ParallelFlowExecutorService(null,null);
        //executionService = new ExecutionServiceImpl();
        scenarioExecutor = new ScenarioExecutorImpl();
        stepExecutionSleep = new StepExecutionSleepImpl();
        stepExecutionClickCss = new StepExecutionClickCssImpl();
        stepExecutionClickXpath = new StepExecutionClickXpathImpl();
    }

    public WebDriverInitializer getWebDriverInitializer() {
        return webDriverInitializer;
    }

    public ProxySourcesClient getProxySourcesClient() {
        return proxySourcesClient;
    }

    public ScenarioSourceListener getScenarioSourceListener() {
        return scenarioSourceListener;
    }

    public ParallelFlowExecutorService getParallelFlowExecutorService() {
        return parallelFlowExecutorService;
    }

    /*
    public ExecutionService getExecutionService() {
        return executionService;
    }
     */

    public ScenarioExecutor getScenarioExecutor() {
        return scenarioExecutor;
    }

    public StepExecutionSleep getStepExecutionSleep() {
        return stepExecutionSleep;
    }

    public StepExecutionClickXpath getStepExecutionClickXpath() {
        return stepExecutionClickXpath;
    }

    public StepExecutionClickCss getStepExecutionClickCss() {
        return stepExecutionClickCss;
    }

}
