package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.QueueData;
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
        var queueData = new QueueData(5);

        var queue = new ProxySourceQueue(queueData);
        queue.putProxy(proxy);

        var result = queue.getProxy();

        assertEquals(proxy, result);
    }
}
