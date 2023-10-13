package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.QueueData;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * The {@code ProxySourceQueue} class represents a thread-safe queue for storing
 * and retrieving instances of {@link ProxyConfigHolder}.
 * It provides methods for putting and getting proxy configurations.
 * <p>
 * This class uses a blocking queue internally to ensure thread safety when
 * adding and retrieving proxy configurations.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see BlockingQueue
 * @see InterruptedException
 * @see RuntimeException
 */
public class ProxySourceQueue {

    private final BlockingQueue<ProxyConfigHolder> queue;

    public ProxySourceQueue(QueueData data) {
        this.queue = new LinkedBlockingDeque<>(data.getCapacity());
    }

    public void putProxy(ProxyConfigHolder proxy) {
        try {
            queue.put(proxy);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public ProxyConfigHolder getProxy() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
