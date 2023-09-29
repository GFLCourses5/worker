package executor.service.service.parallel;

import executor.service.service.ItemHandler;
import executor.service.service.Listener;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ScenarioSourceListener;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

/**
 * Worker task.
 *
 * @author Oleksandr Tuleninov, Yurii Kotsiuba.
 * @version 01
 * */
public class TaskWorker<T> implements Callable<BlockingQueue<T>> {

    private final Listener listener;

    public TaskWorker(Listener listener) {
        this.listener = listener;
    }

    @Override
    public BlockingQueue<T> call() {
        BlockingQueue<T> queue = new LinkedBlockingDeque<>();
        Consumer<T> itemHandlerConsumer = items -> {
            try {
                queue.put(items);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

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
