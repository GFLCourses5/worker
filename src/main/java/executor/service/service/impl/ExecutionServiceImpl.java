package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.WebDriverConfig;
import executor.service.service.ExecutionService;
import executor.service.service.ScenarioExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionServiceImpl implements ExecutionService {
    private static final Logger log = LoggerFactory.getLogger(ExecutionServiceImpl.class);

    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverConfig webDriverConfig;

    public ExecutionServiceImpl(ScenarioExecutor scenarioExecutor, WebDriverConfig webDriverConfig) {
        this.scenarioExecutor = scenarioExecutor;
        this.webDriverConfig = webDriverConfig;
    }

    @Override
    public void execute(Scenario scenario, ProxyConfigHolder proxy) {
        WebDriver webDriver = getWebDriverPrototype(webDriverConfig, proxy);
        if (webDriver == null) return;

        scenarioExecutor.execute(scenario, webDriver);
    }

    private WebDriver getWebDriverPrototype(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
        //TODO: add WebDriverInitializer
       // WebDriverInitializer webDriverInitializer = new WebDriverInitializerImpl();
       // return webDriverInitializer.getInstance(webDriverConfig, proxyConfigHolder);
        return null;
    }
}
