package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.service.ItemHandler;
import executor.service.service.ProxySourceClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * The {@code ProxyTaskWorker} class represents a worker task that retrieves
 * and processes proxy configurations from a {@link ProxySourceClient}.
 * It implements the {@link Runnable} interface to perform its work asynchronously.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ItemHandler
 * @see Consumer
 */
@Service
public class ProxySourceQueueHandler implements Runnable {

    private final ProxySourceClient listener;
    private final ProxySourceQueue queue;

    public ProxySourceQueueHandler(ProxySourceClient listener,
                                   ProxySourceQueue queue) {
        this.listener = listener;
        this.queue = queue;
    }

    public ProxyConfigHolder getProxy() {
        return queue.getProxy();
    }

    @Override
    public void run() {
        Consumer<ProxyConfigHolder> itemHandlerConsumer = queue::putProxy;

        listener.execute(createHandler(itemHandlerConsumer));
    }

    private ItemHandler<ProxyConfigHolder> createHandler(Consumer<ProxyConfigHolder> consumer) {
        return consumer::accept;
    }
}
