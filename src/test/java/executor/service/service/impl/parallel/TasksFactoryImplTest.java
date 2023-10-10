package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.ProxySourceClient;
import executor.service.service.ScenarioSourceListener;
import executor.service.service.TasksFactory;
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
 * @see ScenarioTaskWorker
 * @see ProxyTaskWorker
 * @see Scenario
 * @see Callable
 * @see Runnable
 */
public class TasksFactoryImplTest {

    private TasksFactory tasksFactory;

    @BeforeEach
    public void setUp() {
        ScenarioTaskWorker scenarioTaskWorker = mock(ScenarioTaskWorker.class);
        ProxyTaskWorker proxyTaskWorker = mock(ProxyTaskWorker.class);
        this.tasksFactory = new TasksFactoryImpl(scenarioTaskWorker, proxyTaskWorker);
    }

    @AfterEach
    void tearDown() {
        this.tasksFactory = null;
    }

    @Test
    public void testCreateTaskWorker() {
        Callable<Scenario> callable = tasksFactory.createScenarioTaskWorker();

        assertNotNull(callable);
    }

    @Test
    public void testCreateExecutionWorker() {
        var executionService = mock(ExecutionService.class);
        var scenario = mock(executor.service.model.Scenario.class);
        var proxy = mock(ProxyConfigHolder.class);

        Runnable runnable = tasksFactory.createExecutionWorker(executionService, scenario, proxy);

        assertNotNull(runnable);
    }
}
