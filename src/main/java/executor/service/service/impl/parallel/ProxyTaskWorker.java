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
 * It implements the {@link Callable} interface to perform its work asynchronously.
 * <p>
 * This class is responsible for executing the {@link ProxySourceClient} to fetch
 * proxy configurations and then stores them in a {@link ProxySourceQueue}.
 * The retrieved proxy configuration is returned as the result of the call.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ProxySourceClient
 * @see ProxySourceQueue
 * @see ItemHandler
 */
@Service
public class ProxyTaskWorker implements Callable<ProxyConfigHolder> {

    private final ProxySourceClient listener;
    private final ProxySourceQueue queue;

    public ProxyTaskWorker(ProxySourceClient listener,
                           ProxySourceQueue queue) {
        this.listener = listener;
        this.queue = queue;
    }

    @Override
    public ProxyConfigHolder call() {
        Consumer<ProxyConfigHolder> itemHandlerConsumer = queue::putProxy;

        listener.execute(createHandler(itemHandlerConsumer));

        return queue.getProxy();
    }

    private ItemHandler<ProxyConfigHolder> createHandler(Consumer<ProxyConfigHolder> consumer) {
        return consumer::accept;
    }
}
