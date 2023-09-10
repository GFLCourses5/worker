package executor.service.service.impl;

import executor.service.config.bean.BeanConfig;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.WebDriverConfig;
import executor.service.service.ExecutionService;
import executor.service.service.ScenarioExecutor;
import executor.service.service.WebDriverInitializer;

import org.openqa.selenium.WebDriver;


/**
 * The facade for execute ScenarioExecutor.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ExecutionServiceImpl implements ExecutionService {

    private ScenarioExecutor scenarioExecutor;
    private BeanConfig beanConfig;

    public ExecutionServiceImpl() {
    }

    public ExecutionServiceImpl(ScenarioExecutor scenarioExecutor,
                                BeanConfig beanConfig) {
        this.scenarioExecutor = scenarioExecutor;
        this.beanConfig = beanConfig;
    }

    /**
     * Execute ScenarioExecutor.
     *
     * @param scenario the scenario
     * @param proxy    the proxy
     */
    @Override
    public void execute(Scenario scenario, ProxyConfigHolder proxy) {
        WebDriver webDriver = getWebDriverPrototype(beanConfig.webDriverConfig(), proxy);
        if (webDriver == null) return;

        scenarioExecutor.execute(scenario, webDriver);
    }

    /**
     * Get WebDriver as Prototype.
     *
     * @param webDriverConfig   the WebDriverConfig entity
     * @param proxyConfigHolder the ProxyConfigHolder entity
     */
    private WebDriver getWebDriverPrototype(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
        WebDriverInitializer webDriverInitializer = new WebDriverInitializerImpl();
        return webDriverInitializer.getInstance(webDriverConfig, proxyConfigHolder);
    }
}
