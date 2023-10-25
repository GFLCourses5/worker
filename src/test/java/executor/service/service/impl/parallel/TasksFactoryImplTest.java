package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.request.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.TasksFactory;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Test class for testing the functionality of the {@code TasksFactoryImpl} class
 * is an implementation of the {@link TasksFactory}.
 * This class contains unit tests to verify that {@code TasksFactoryImpl} is working correctly.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see Scenario
 * @see Callable
 * @see Runnable
 */
public class TasksFactoryImplTest {

    @Test
    public void testCreateExecutionWorker() {
        var executionService = mock(ExecutionService.class);
        var scenario = mock(Scenario.class);
        var proxy = mock(ProxyConfigHolder.class);

        var tasksFactory = new TasksFactoryImpl();
        Runnable runnable = tasksFactory.createExecutionWorker(executionService, scenario, proxy);

        assertNotNull(runnable);
    }
}
