package executor.service.service.impl.parallel;

import executor.service.model.Scenario;
import executor.service.service.ItemHandler;
import executor.service.service.ScenarioSourceListener;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ScenarioTaskWorker} class
 * is an implementation of the {@link Callable}.
 * This class contains unit tests to verify that {@code ScenarioTaskWorker} is working correctly.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ScenarioSourceListener
 * @see ScenarioSourceQueue
 * @see Scenario
 */
public class ScenarioTaskWorkerTest {

    @Test
    public void testScenarioTaskWorkerCall() {
        var scenario = new Scenario();

        var listener = mock(ScenarioSourceListener.class);
        var queue = mock(ScenarioSourceQueue.class);
        doNothing().when(listener).execute(any(ItemHandler.class));
        when(queue.getScenario()).thenReturn(scenario);

        var scenarioTaskWorker = new ScenarioTaskWorker(listener, queue);
        var result = scenarioTaskWorker.call();

        verify(listener, times(1)).execute(any(ItemHandler.class));
        assertNotNull(result);
        verifyNoMoreInteractions(listener);
    }
}