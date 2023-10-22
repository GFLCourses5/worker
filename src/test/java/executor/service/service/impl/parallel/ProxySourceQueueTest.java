package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.QueueData;
import executor.service.service.ItemHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ItemQueue} class.
 * This class contains unit tests to verify that {@code ItemQueue} is working correctly.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ProxySourceQueue
 * @see ProxyConfigHolder
 */
public class ProxySourceQueueTest {

    private ProxySourceQueueHandler handler;
    private ItemHandler itemHandler;
    private Consumer<ProxyConfigHolder> itemHandlerConsumer;
    private ProxySourceQueue queue;

    @BeforeEach
    void setUp() {
        handler = Mockito.mock(ProxySourceQueueHandler.class);
        itemHandler = Mockito.mock(ItemHandler.class);
        itemHandlerConsumer = Mockito.mock(Consumer.class);
        queue = new ProxySourceQueue(new QueueData(5));
    }

    @Test
    void testPutAndGetProxy() {
        var proxy = new ProxyConfigHolder();
        var queueData = new QueueData(5);

        var queue = new ProxySourceQueue(queueData);
        queue.putProxy(proxy);

        var result = queue.getProxy();

        assertEquals(proxy, result);
    }

    @Test
    void testOnDeadlockWhenHandlerCrash() throws ExecutionException, InterruptedException, TimeoutException {
        ProxyConfigHolder holder = new ProxyConfigHolder();

        doAnswer(invocation -> {
                    queue.putProxy(holder);
                    queue.putProxy(holder);
                    return null;
                }).when(handler).run();

        when(handler.getProxy()).thenReturn(holder).thenThrow(new RuntimeException()).thenReturn(holder);

        FutureTask<List<ProxyConfigHolder>> task = new FutureTask(
                () -> {
                    List<ProxyConfigHolder> list = new ArrayList<>();
                    list.add(queue.getProxy());
                    try {
                        list.add(queue.getProxy());
                    } catch (Exception e){
                    }
                    list.add(queue.getProxy());
                    return list;
                }
        );
        List<ProxyConfigHolder> proxyConfigHolders = task.get(2l, TimeUnit.SECONDS);

        assertFalse(proxyConfigHolders.isEmpty());
        assertEquals(2, proxyConfigHolders.size());

    }

}
