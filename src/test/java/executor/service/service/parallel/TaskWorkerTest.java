package executor.service.service.parallel;

import executor.service.service.ItemHandler;
import executor.service.service.Listener;
import executor.service.service.parallel.ItemQueue;
import executor.service.service.parallel.TaskWorker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code TaskWorker} class
 * is an implementation of the {@link Callable}.
 * This class contains unit tests to verify that {@code TaskWorker} is working correctly.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see executor.service.service.Listener
 * @see executor.service.service.parallel.TaskWorker
 * @see executor.service.service.ItemHandler
 */
public class TaskWorkerTest {

    private Listener listener;
    private TaskWorker scenarioTaskWorker;
    private TaskWorker proxyTaskWorker;

    @BeforeEach
    public void setUp() {
        this.listener = mock(Listener.class);
        this.scenarioTaskWorker = new TaskWorker(listener);
        this.proxyTaskWorker = new TaskWorker(listener);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(listener);
        verifyNoMoreInteractions(listener);
        this.listener = null;
    }

    @Test
    public void testScenarioTaskWorkerCall() {
        doNothing().when(listener).execute(any(ItemHandler.class));

        var result = scenarioTaskWorker.call();

        verify(listener, times(1)).execute(any(ItemHandler.class));

        assertNotNull(result);
    }

    @Test
    public void testProxyTaskWorkerCall() {
        doNothing().when(listener).execute(any(ItemHandler.class));

        var result = proxyTaskWorker.call();

        verify(listener, times(1)).execute(any(ItemHandler.class));

        assertNotNull(result);
    }
}
