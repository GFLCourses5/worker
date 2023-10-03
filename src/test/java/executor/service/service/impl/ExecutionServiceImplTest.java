package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.WebDriverConfig;
import executor.service.service.ExecutionService;
import executor.service.service.ScenarioExecutor;
import executor.service.service.WebDriverInitializer;
import executor.service.service.impl.webDriver.WebDriverInitializerImpl;
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
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();
        WebDriverConfig webDriverConfig = new WebDriverConfig();

        WebDriver webDriver = mock(WebDriver.class);
        WebDriverInitializer initializer = mock(WebDriverInitializerImpl.class);
        ScenarioExecutor scenarioExecutor = mock(ScenarioExecutor.class);

        doNothing().when(scenarioExecutor).execute(any(Scenario.class), any(WebDriver.class));
        when(initializer.getInstance(webDriverConfig, proxyConfigHolder)).thenReturn(webDriver);

        ExecutionService executionService = new ExecutionServiceImpl(scenarioExecutor, webDriverConfig, initializer);
        executionService.execute(scenario, proxyConfigHolder);

        verify(scenarioExecutor, times(1)).execute(any(Scenario.class), any(WebDriver.class));
        verifyNoMoreInteractions(scenarioExecutor);
    }
}
