package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.request.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.ScenarioExecutor;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

/**
 * The {@code ExecutionServiceImpl} class implements the {@link ExecutionService} interface and
 * is responsible for executing scenarios using a WebDriver.
 * It utilizes a {@link ScenarioExecutor} to perform the execution and relies on
 * {@link WebDriverInitializer} for configuring and initializing WebDriver instances.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see WebDriver
 * @see Scenario
 * @see ProxyConfigHolder
 */
@Service
public class ExecutionServiceImpl implements ExecutionService {

    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverInitializer webDriverInitializer;

    public ExecutionServiceImpl(ScenarioExecutor scenarioExecutor,
                                WebDriverInitializer webDriverInitializer) {
        this.scenarioExecutor = scenarioExecutor;
        this.webDriverInitializer = webDriverInitializer;
    }

    /**
     * Executes a scenario using the provided WebDriver and optional proxy configuration.
     *
     * @param scenario the scenario
     * @param proxy    the proxy
     */
    @Override
    public void execute(Scenario scenario, ProxyConfigHolder proxy) {
        WebDriver webDriver = getWebDriver(proxy);

        scenarioExecutor.execute(scenario, webDriver);
    }

    /**
     * Retrieves a WebDriver instance as a prototype.
     *
     * @param proxyConfigHolder the ProxyConfigHolder entity
     */
    private WebDriver getWebDriver(ProxyConfigHolder proxyConfigHolder) {
        return webDriverInitializer.getInstance(proxyConfigHolder);
    }
}
