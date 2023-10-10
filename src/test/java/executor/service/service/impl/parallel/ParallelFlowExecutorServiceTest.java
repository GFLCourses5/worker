package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.ProxySourceClient;
import executor.service.service.TasksFactory;
import executor.service.service.impl.ExecutionServiceImpl;
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
 * @see ProxySourceClient
 * @see executor.service.service.ProxySourceClient
 */
public class ParallelFlowExecutorServiceTest {

    private ExecutorService threadPoolExecutor;
    private ExecutionServiceImpl service;
    private TasksFactory tasksFactory;
    private ParallelFlowExecutorServiceImpl parallelFlowExecutorService;

    @BeforeEach
    void setUp() {
        this.threadPoolExecutor = mock(ExecutorService.class);
        this.service = mock(ExecutionServiceImpl.class);
        this.tasksFactory = mock(TasksFactory.class);
        this.parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(threadPoolExecutor, service, tasksFactory);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(threadPoolExecutor);
        verifyNoMoreInteractions(service);
        verifyNoMoreInteractions(tasksFactory);
        this.threadPoolExecutor = null;
        this.service = null;
        this.tasksFactory = null;
        this.parallelFlowExecutorService = null;
    }

    @Test
    public void testPositiveParallelFlowExecutorServiceExecute() throws Exception {
        var scenario = new Scenario();
        var proxy = new ProxyConfigHolder();

        Future<Scenario> futureScenario = mock(Future.class);
        Future<ProxyConfigHolder> futureProxy = mock(Future.class);

        AtomicReference<Runnable> executionWorker = new AtomicReference<>(mock(Runnable.class));

        Callable<Scenario> callableScenario = mock(Callable.class);
        when(callableScenario.call()).thenReturn(scenario);
        when(tasksFactory.createScenarioTaskWorker()).thenReturn(callableScenario);

        Callable<ProxyConfigHolder> callableProxy = mock(Callable.class);
        when(callableProxy.call()).thenReturn(proxy);
        when(tasksFactory.createProxyTaskWorker()).thenReturn(callableProxy);

        Runnable runnable = mock(Runnable.class);
        when(tasksFactory.createExecutionWorker(service, scenario, proxy)).thenReturn(runnable);

        when(threadPoolExecutor.submit(any(Callable.class))).thenReturn(futureScenario, futureProxy);
        doAnswer(invocation -> {
            executionWorker.set((Runnable) invocation.getArguments()[0]);
            changeWhileCycleFlagField();
            return null;
        }).when(threadPoolExecutor).execute(any(Runnable.class));

        when(futureScenario.get()).thenReturn(scenario);
        when(futureProxy.get()).thenReturn(proxy);

        parallelFlowExecutorService.execute();

        verify(tasksFactory, times(1)).createScenarioTaskWorker();
        verify(tasksFactory, times(1)).createProxyTaskWorker();
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
