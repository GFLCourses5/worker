//package executor.service.service.impl.parallel;
//
//import executor.service.model.ProxyConfigHolder;
//import executor.service.model.Scenario;
//import executor.service.service.ExecutionService;
//import executor.service.service.ParallelFlowExecutorService;
//import executor.service.service.TasksFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.lang.reflect.Field;
//import java.util.concurrent.ExecutorService;
//
//import static org.mockito.Mockito.*;
//
///**
// * Test class for testing the functionality of the {@code ParallelFlowExecutorServiceImpl} class
// * is an implementation of the {@link ParallelFlowExecutorService}.
// * This class contains unit tests to verify that {@code ParallelFlowExecutorServiceImpl} is working correctly.
// *
// * @author Oleksandr Tuleninov
// * @version 01
// * @see ExecutorService
// * @see ScenarioSourceQueueHandler
// * @see ProxySourceQueueHandler
// * @see Runnable
// */
//public class ParallelFlowExecutorServiceTest {
//
//    private ParallelFlowExecutorServiceImpl parallelFlowExecutorService;
//    private ExecutorService threadPoolExecutor;
//    private ScenarioSourceQueueHandler scenarioHandler;
//    private ProxySourceQueueHandler proxyHandler;
//    private Runnable runnable;
//
//    @BeforeEach
//    public void setUp() {
//        var scenario = new Scenario();
//        var proxy = new ProxyConfigHolder();
//
//        threadPoolExecutor = Mockito.mock(ExecutorService.class);
//        scenarioHandler = Mockito.mock(ScenarioSourceQueueHandler.class);
//        proxyHandler = Mockito.mock(ProxySourceQueueHandler.class);
//        TasksFactory tasksFactory = Mockito.mock(TasksFactory.class);
//        ExecutionService service = Mockito.mock(ExecutionService.class);
//
//        when(scenarioHandler.getScenario()).thenReturn(scenario);
//        when(proxyHandler.getProxy()).thenReturn(proxy);
//
//        runnable = mock(Runnable.class);
//        when(tasksFactory.createExecutionWorker(service, scenario, proxy)).thenReturn(runnable);
//
//        doNothing().when(threadPoolExecutor).execute(scenarioHandler);
//        doNothing().when(threadPoolExecutor).execute(proxyHandler);
//
//        doAnswer(invocation -> {
//            changeWhileCycleFlagField();
//            return null;
//        }).when(threadPoolExecutor).execute(runnable);
//
//        parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(
//                threadPoolExecutor, scenarioHandler, proxyHandler, tasksFactory, service);
//    }
//
//    @Test
//    public void testExecute() {
//        parallelFlowExecutorService.execute();
//
//        verify(threadPoolExecutor, times(1)).execute(scenarioHandler);
//        verify(threadPoolExecutor, times(1)).execute(proxyHandler);
//        verify(threadPoolExecutor, times(1)).execute(runnable);
//    }
//
//    @Test
//    public void testShutdown() {
//        parallelFlowExecutorService.shutdown();
//
//        verify(threadPoolExecutor).shutdown();
//    }
//
//    private void changeWhileCycleFlagField() throws NoSuchFieldException, IllegalAccessException {
//        Field flagField = parallelFlowExecutorService.getClass().getDeclaredField("FLAG");
//        flagField.setAccessible(true);
//        flagField.set(parallelFlowExecutorService, false);
//    }
//}
