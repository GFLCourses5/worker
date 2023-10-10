package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;

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
 * @see ProxyConfigHolder
 * @see BlockingQueue
 */
public class ProxySourceQueue {

    private final BlockingQueue<ProxyConfigHolder> queue;

    public ProxySourceQueue() {
        this.queue = new LinkedBlockingDeque<>();
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