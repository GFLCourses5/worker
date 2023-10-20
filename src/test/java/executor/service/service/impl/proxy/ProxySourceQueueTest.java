package executor.service.service.impl.proxy;

import executor.service.model.ProxyConfigHolder;
import executor.service.service.impl.proxy.ProxySourceQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void testPutAndGetProxy() {
        var proxy = new ProxyConfigHolder();

        var queue = new ProxySourceQueue();
        queue.putProxy(proxy);

        var result = queue.getProxy();

        assertEquals(proxy, result);
    }
}
