package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.request.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.TasksFactory;
import executor.service.service.impl.proxy.ProxySourceQueueHandler;
import executor.service.service.impl.scenario.ScenarioSourceQueueHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ParallelFlowExecutorServiceImpl} class
 * is an implementation of the {@link ParallelFlowExecutorService}.
 * This class contains unit tests to verify that {@code ParallelFlowExecutorServiceImpl} is working correctly.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ExecutorService
 * @see Runnable
 * @see ParallelFlowExecutorServiceImpl
 * @see ScenarioSourceQueueHandler
 * @see ProxySourceQueueHandler
 * @see TasksFactory
 */
public class ParallelFlowExecutorServiceTest {

    private ExecutorService threadPoolExecutor;
    private Runnable runnable;
    private ParallelFlowExecutorServiceImpl parallelFlowExecutorService;

    @BeforeEach
    public void setUp() {
        threadPoolExecutor = Mockito.mock(ExecutorService.class);
        runnable = mock(Runnable.class);
        ScenarioSourceQueueHandler scenarioHandler = Mockito.mock(ScenarioSourceQueueHandler.class);
        ProxySourceQueueHandler proxyHandler = Mockito.mock(ProxySourceQueueHandler.class);
        TasksFactory tasksFactory = Mockito.mock(TasksFactory.class);
        ExecutionService service = Mockito.mock(ExecutionService.class);

        var scenario = new Scenario();
        var proxy = new ProxyConfigHolder();

        when(scenarioHandler.getScenario()).thenReturn(scenario);
        when(proxyHandler.getProxy()).thenReturn(proxy);
        when(tasksFactory.createExecutionWorker(service, scenario, proxy)).thenReturn(runnable);
        doAnswer(invocation -> {
            changeWhileCycleFlagField();
            return null;
        }).when(threadPoolExecutor).execute(runnable);

        parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(
                threadPoolExecutor, scenarioHandler, proxyHandler, tasksFactory, service);
    }

    @Test
    public void testExecute() {
        parallelFlowExecutorService.execute();

        verify(threadPoolExecutor, times(1)).execute(runnable);
    }

    @Test
    public void testShutdown() throws InterruptedException {
        when(threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS)).thenReturn(true);

        parallelFlowExecutorService.shutdown();

        verify(threadPoolExecutor).shutdown();
    }

    private void changeWhileCycleFlagField() throws NoSuchFieldException, IllegalAccessException {
        Field flagField = parallelFlowExecutorService.getClass().getDeclaredField("FLAG");
        flagField.setAccessible(true);
        flagField.set(parallelFlowExecutorService, false);
    }
}
