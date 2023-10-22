package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.ScenarioExecutor;
import executor.service.service.TasksFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeadlockParallelFlowExecutorServiceImplTest {

    private ParallelFlowExecutorServiceImpl parallelFlowExecutorService;

    @Autowired
    private ExecutorService threadPoolExecutor;
    private ScenarioSourceQueueHandler scenarioHandler;
    private ProxySourceQueueHandler proxyHandler;

    private ScenarioExecutor scenarioExecutor;

    @Autowired
    private TasksFactory tasksFactory;

    private ExecutionService executionService;
    @BeforeEach
    void setUp() {
        scenarioHandler = Mockito.mock(ScenarioSourceQueueHandler.class);
        proxyHandler = Mockito.mock(ProxySourceQueueHandler.class);
        scenarioExecutor = Mockito.mock(ScenarioExecutor.class);
        executionService = Mockito.mock(ExecutionService.class);
        threadPoolExecutor = Executors.newCachedThreadPool();
        tasksFactory = new TasksFactoryImpl();
        parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(
                threadPoolExecutor, scenarioHandler, proxyHandler, tasksFactory, executionService);
    }

    @Test
    public void whenParallelFlowGetRuntimeException() {
        Scenario scenario = new Scenario();
        ProxyConfigHolder proxy = new ProxyConfigHolder();

        when(proxyHandler.getProxy()).thenReturn(proxy).thenReturn(proxy).thenReturn(proxy);
        when(scenarioHandler.getScenario()).thenReturn(scenario).thenThrow(new RuntimeException()).thenReturn(scenario);

        doNothing().when(scenarioExecutor).execute(any(Scenario.class),any(WebDriver.class));

        parallelFlowExecutorService.execute();
        //parallelFlowExecutorService.shutdown();

        //verify(executionService, times(2)).execute(any(Scenario.class), any(ProxyConfigHolder.class));
        assertThrows(RuntimeException.class, () -> parallelFlowExecutorService.execute() );

    }



}