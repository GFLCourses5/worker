package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.request.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.impl.proxy.ProxySourceQueueHandler;
import executor.service.service.impl.scenario.ScenarioSourceQueueHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
 */
public class ParallelFlowExecutorServiceTest {

    private final ExecutorService threadPoolExecutor = Mockito.mock(ExecutorService.class);;
    private ParallelFlowExecutorServiceImpl parallelFlowExecutorService;
    private final ScenarioSourceQueueHandler scenarioHandler = Mockito.mock(ScenarioSourceQueueHandler.class);
    private final ProxySourceQueueHandler proxyHandler = Mockito.mock(ProxySourceQueueHandler.class);
    private final ExecutionService service = Mockito.mock(ExecutionService.class);


    @Test
    public void testExecute() {
        when(scenarioHandler.getScenario()).thenReturn(new Scenario());
        when(proxyHandler.getProxy()).thenReturn(new ProxyConfigHolder());
        doNothing().when(service).execute(any(Scenario.class), any(ProxyConfigHolder.class));

        parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(
                threadPoolExecutor, scenarioHandler, proxyHandler, service);

        parallelFlowExecutorService.execute();

        verify(threadPoolExecutor, atLeast(1)).execute(any(Runnable.class));
        threadPoolExecutor.shutdown();
    }

    @Test
    public void testShutdown() throws InterruptedException {
        when(scenarioHandler.getScenario()).thenReturn(new Scenario());
        when(proxyHandler.getProxy()).thenReturn(new ProxyConfigHolder());
        doNothing().when(service).execute(any(Scenario.class), any(ProxyConfigHolder.class));

        parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(
                threadPoolExecutor, scenarioHandler, proxyHandler, service);

        parallelFlowExecutorService.execute();
        verify(threadPoolExecutor, atLeast(1)).execute(any(Runnable.class));
        parallelFlowExecutorService.shutdown();

        parallelFlowExecutorService.execute();

        List<Runnable> runnables = threadPoolExecutor.shutdownNow();
        assertTrue(runnables.isEmpty());
        verify(threadPoolExecutor).shutdown();
    }


}
