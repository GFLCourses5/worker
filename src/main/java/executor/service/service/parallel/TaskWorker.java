package executor.service.service.parallel;

import executor.service.service.ItemHandler;
import executor.service.service.Listener;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ScenarioSourceListener;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * Worker task.
 *
 * @author Oleksandr Tuleninov, Yurii Kotsiuba.
 * @version 01
 */
public class TaskWorker<T> implements Callable<ItemQueue<T>> {

    private final Listener listener;

    public TaskWorker(Listener listener) {
        this.listener = listener;
    }

    @Override
    public ItemQueue<T> call() {
        ItemQueue<T> queue = new ItemQueue<>();
        Consumer<T> itemHandlerConsumer = queue::putItem;

        if (listener instanceof ScenarioSourceListener) {
            listener.execute(createHandler(itemHandlerConsumer));
        } else if (listener instanceof ProxySourcesClient) {
            listener.execute(createHandler(itemHandlerConsumer));
        }

        return queue;
    }

    private ItemHandler<T> createHandler(Consumer<T> consumer) {
        return consumer::accept;
    }
}
