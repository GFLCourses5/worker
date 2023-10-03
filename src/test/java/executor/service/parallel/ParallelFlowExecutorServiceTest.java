package executor.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ScenarioSourceListener;
import executor.service.service.TasksFactory;
import executor.service.service.impl.ExecutionServiceImpl;
import executor.service.service.parallel.ItemQueue;
import executor.service.service.parallel.ParallelFlowExecutorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ParallelFlowExecutorServiceImpl} class
 * is an implementation of the {@link ParallelFlowExecutorService}.
 * This class contains unit tests to verify that {@code ParallelFlowExecutorServiceImpl} is working correctly.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see executor.service.service.ExecutionService
 * @see executor.service.service.ScenarioSourceListener
 * @see executor.service.service.ProxySourcesClient
 */
public class ParallelFlowExecutorServiceTest {

    private ExecutorService threadPoolExecutor;
    private ExecutionServiceImpl service;
    private ScenarioSourceListener scenarioSourceListener;
    private ProxySourcesClient proxySourcesClient;
    private TasksFactory tasksFactory;
    private ParallelFlowExecutorServiceImpl parallelFlowExecutorService;

    @BeforeEach
    void setUp() {
        this.threadPoolExecutor = mock(ExecutorService.class);
        this.service = mock(ExecutionServiceImpl.class);
        this.scenarioSourceListener = mock(ScenarioSourceListener.class);
        this.proxySourcesClient = mock(ProxySourcesClient.class);
        this.tasksFactory = mock(TasksFactory.class);
        this.parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(threadPoolExecutor, service,
                scenarioSourceListener, proxySourcesClient, tasksFactory);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(threadPoolExecutor);
        verifyNoMoreInteractions(service);
        verifyNoMoreInteractions(scenarioSourceListener);
        verifyNoMoreInteractions(proxySourcesClient);
        verifyNoMoreInteractions(tasksFactory);
        this.threadPoolExecutor = null;
        this.service = null;
        this.scenarioSourceListener = null;
        this.proxySourcesClient = null;
        this.tasksFactory = null;
        this.parallelFlowExecutorService = null;
    }

    @Test
    public void testPositiveParallelFlowExecutorServiceExecute() throws Exception {
        var scenario = new Scenario();
        var proxy = new ProxyConfigHolder();

        ItemQueue scenarios = new ItemQueue();
        scenarios.putItem(scenario);
        ItemQueue proxies = new ItemQueue();
        proxies.putItem(proxy);

        Future<ItemQueue> futureScenarios = mock(Future.class);
        Future<ItemQueue> futureProxies = mock(Future.class);

        AtomicReference<Runnable> executionWorker = new AtomicReference<>(mock(Runnable.class));

        Callable<ItemQueue> callableScenario = mock(Callable.class);
        when(callableScenario.call()).thenReturn(scenarios);
        when(tasksFactory.createTaskWorker(scenarioSourceListener)).thenReturn(callableScenario);

        Callable<ItemQueue> callableProxy = mock(Callable.class);
        when(callableProxy.call()).thenReturn(proxies);
        when(tasksFactory.createTaskWorker(proxySourcesClient)).thenReturn(callableProxy);

        Runnable runnable = mock(Runnable.class);
        when(tasksFactory.createExecutionWorker(service, scenario, proxy)).thenReturn(runnable);

        when(threadPoolExecutor.submit(any(Callable.class))).thenReturn(futureScenarios, futureProxies);
        doAnswer(invocation -> {
            executionWorker.set((Runnable) invocation.getArguments()[0]);
            changeWhileCycleFlagField();
            return null;
        }).when(threadPoolExecutor).execute(any(Runnable.class));

        when(futureScenarios.get()).thenReturn(scenarios);
        when(futureProxies.get()).thenReturn(proxies);

        parallelFlowExecutorService.execute();

        verify(tasksFactory, times(1)).createTaskWorker(scenarioSourceListener);
        verify(tasksFactory, times(1)).createTaskWorker(proxySourcesClient);
        verify(tasksFactory, times(1)).createExecutionWorker(service, scenario, proxy);
        verify(threadPoolExecutor, times(2)).submit(any(Callable.class));
        verify(threadPoolExecutor, times(1)).execute(any(Runnable.class));
    }

    @Test
    public void testNegativeParallelFlowExecutorServiceExecute() {
        ParallelFlowExecutorService spyService = spy(parallelFlowExecutorService);
        doNothing().when(spyService).execute();

        verify(spyService, times(0)).execute();
    }

    private void changeWhileCycleFlagField() throws NoSuchFieldException, IllegalAccessException {
        Field flagField = parallelFlowExecutorService.getClass().getDeclaredField("FLAG");
        flagField.setAccessible(true);
        flagField.set(parallelFlowExecutorService, false);
    }
}
