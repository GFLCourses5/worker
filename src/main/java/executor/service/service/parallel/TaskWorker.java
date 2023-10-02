package executor.service.service.parallel;

import executor.service.service.Item;
import executor.service.service.ItemHandler;
import executor.service.service.Listener;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * The {@code TaskWorker} class is a generic implementation
 * of the {@link Callable} interface,
 * responsible for creating task workers
 * that produce items and add them to a specified queue.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see executor.service.service.Listener
 * @see executor.service.service.parallel.ItemQueue
 * @see Consumer
 */
public class TaskWorker implements Callable<ItemQueue> {

    private final Listener listener;

    public TaskWorker(Listener listener) {
        this.listener = listener;
    }

    @Override
    public ItemQueue call() {
        ItemQueue queue = new ItemQueue();
        Consumer<Item> itemHandlerConsumer = queue::putItem;

        listener.execute(createHandler(itemHandlerConsumer));

        return queue;
    }

    private ItemHandler<Item> createHandler(Consumer<Item> consumer) {
        return consumer::accept;
    }
}
