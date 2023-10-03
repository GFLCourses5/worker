package executor.service.service.impl.parallel;

import executor.service.service.Item;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@code ItemQueue} class is a generic class representing a custom blocking queue
 * for storing and retrieving items of a specified type.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see LinkedBlockingQueue
 */
public class ItemQueue {

    private final BlockingQueue<Item> queue;

    public ItemQueue() {
        this.queue = new LinkedBlockingQueue<>();
    }

    public void putItem(Item item) {
        try {
            queue.put(item);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Item getItem() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
