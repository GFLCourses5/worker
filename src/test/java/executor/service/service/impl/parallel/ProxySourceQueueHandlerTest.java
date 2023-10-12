package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.service.ItemHandler;
import executor.service.service.ProxySourceClient;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Test class for testing the functionality of the {@code ProxyTaskWorkerTest} class
 * is an implementation of the {@link Callable}.
 * This class contains unit tests to verify that {@code ProxyTaskWorkerTest} is working correctly.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ProxySourceClient
 * @see ProxySourceQueue
 * @see ProxyConfigHolder
 */
public class ProxySourceQueueHandlerTest {

    @Test
    public void testScenarioTaskWorkerCall() {
        var proxy = new ProxyConfigHolder();

        var listener = mock(ProxySourceClient.class);
        var queue = mock(ProxySourceQueue.class);
        doNothing().when(listener).execute(any(ItemHandler.class));
        when(queue.getProxy()).thenReturn(proxy);

        var proxyHandler = new ProxySourceQueueHandler(listener, queue);
        proxyHandler.run();

        verify(listener, times(1)).execute(any(ItemHandler.class));
        assertEquals(proxy, proxyHandler.getProxy());
        verifyNoMoreInteractions(listener);
    }
}
