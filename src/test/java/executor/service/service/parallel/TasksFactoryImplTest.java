package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.ScenarioSourceListener;
import executor.service.service.TasksFactory;
import executor.service.service.parallel.ItemQueue;
import executor.service.service.parallel.TasksFactoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
 * @see executor.service.service.Listener
 * @see executor.service.service.parallel.ItemQueue
 * @see Callable
 * @see Runnable
 */
public class TasksFactoryImplTest {

    private TasksFactory tasksFactory;

    @BeforeEach
    public void setUp() {
        this.tasksFactory = new TasksFactoryImpl();
    }

    @AfterEach
    void tearDown() {
        this.tasksFactory = null;
    }

    @Test
    public void testCreateTaskWorker() {
        Callable<ItemQueue> callable = tasksFactory.createTaskWorker(mock(ScenarioSourceListener.class));

        assertNotNull(callable);
    }

    @Test
    public void testCreateExecutionWorker() {
        var executionService = mock(ExecutionService.class);
        var scenario = mock(Scenario.class);
        var proxy = mock(ProxyConfigHolder.class);

        Runnable runnable = tasksFactory.createExecutionWorker(executionService, scenario, proxy);

        assertNotNull(runnable);
    }
}
