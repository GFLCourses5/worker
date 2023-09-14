package executor.service.service.executionservices.impl;

import executor.service.model.config.WebDriverConfig;
import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;
import executor.service.service.executionservices.ExecutionService;
import executor.service.service.executionservices.ScenarioExecutor;
import executor.service.service.webdriver.WebDriverInitializer;
import executor.service.service.webdriver.WebDriverInitializerImpl;
import org.openqa.selenium.WebDriver;

/**
 * ExecutionService facade.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ExecutionServiceImpl implements ExecutionService {

  private final ScenarioExecutor scenarioExecutor;
  private final WebDriverConfig webDriverConfig;

  public ExecutionServiceImpl(ScenarioExecutor scenarioExecutor,
      WebDriverConfig webDriverConfig) {
    this.scenarioExecutor = scenarioExecutor;
    this.webDriverConfig = webDriverConfig;
  }

  /**
   * Execute ScenarioExecutor.
   *
   * @param scenario the scenario
   * @param proxy    the proxy
   */
  @Override
  public void execute(Scenario scenario, ProxyConfigHolder proxy) {
    WebDriver webDriver = getWebDriverPrototype(webDriverConfig, proxy);

    scenarioExecutor.execute(scenario, webDriver);
  }

  /**
   * Get WebDriver as Prototype.
   *
   * @param webDriverConfig   the WebDriverConfig entity
   * @param proxyConfigHolder the ProxyConfigHolder entity
   */
  private WebDriver getWebDriverPrototype(WebDriverConfig webDriverConfig,
      ProxyConfigHolder proxyConfigHolder) {
    WebDriverInitializer webDriverInitializer = new WebDriverInitializerImpl();
    return webDriverInitializer.getInstance(webDriverConfig, proxyConfigHolder);
  }
}
