package executor.service.service.impl;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.*;
import executor.service.service.ExecutionService;
import executor.service.service.ScenarioExecutor;
import executor.service.service.WebDriverInitializer;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ExecutionServiceImpl} class.
 * This class contains unit tests to verify that {@code ExecutionServiceImpl} is working correctly.
 *
 *  @author  Mykyta Hurmaza
 *  @version 01
 * */
public class ExecutionServiceImplTest {
    @Test
    public void testWorkMethodExecute() {
        Scenario scenario = new Scenario();
        ProxyConfigHolder proxyConfigHolder = getProxyConfigHolder();
        WebDriverConfig webDriverConfig = getWebDriverConfig();
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        WebDriverInitializer initializer = new WebDriverInitializerImpl(propertiesConfig);

        ScenarioExecutor scenarioExecutor = mock(ScenarioExecutor.class);
        doNothing().when(scenarioExecutor).execute(any(Scenario.class), any(WebDriver.class));

        ExecutionService executionService = new ExecutionServiceImpl(scenarioExecutor, webDriverConfig, initializer);
        executionService.execute(scenario, proxyConfigHolder);

        verify(scenarioExecutor, times(1)).execute(any(Scenario.class), any(WebDriver.class));
        verifyNoMoreInteractions(scenarioExecutor);
    }

    private WebDriverConfig getWebDriverConfig() {
        return new WebDriverConfig(
                "chromedriver.exe",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36",
                15000L,
                5000L);
    }

    private ProxyConfigHolder getProxyConfigHolder() {
        return new ProxyConfigHolder(
                new ProxyNetworkConfig("host1", 8080),
                new ProxyCredentials("user11", "pass1"));
    }
}
