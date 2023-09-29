package executor.service.service.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Custom BlockingQueue<>().
 *
 * @author Oleksandr Tuleninov.
 * @version 01
 * */
public class ItemQueue<T> {

    private final BlockingQueue<T> queue;

    public ItemQueue() {
        this.queue = new LinkedBlockingQueue<>();
    }

    public void putItem(T item) {
        try {
            queue.put(item);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getItem() {
        try {
            return (T) queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
