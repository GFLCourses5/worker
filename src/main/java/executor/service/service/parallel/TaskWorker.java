package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ItemHandler;
import executor.service.service.ProxyHandler;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ScenarioSourceListener;
import reactor.core.publisher.Flux;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class TaskWorker<T> implements Runnable {

    private final T listener;
    private final BlockingQueue<T> queue;

    public TaskWorker(T listener, BlockingQueue<T> queue) {
        this.listener = listener;
        this.queue = queue;
    }

    @Override
    public void run() {
//        Consumer<ProxyConfigHolder> itemHandlerConsumer = items -> {
//            try {
//                queue.put(items);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        };
//
//        if (listener instanceof ScenarioSourceListener) {
//            ((ScenarioSourceListener) listener).execute(createItemHandler(itemHandlerConsumer));
//        } else if (listener instanceof ProxySourcesClient) {
//            ((ProxySourcesClient) listener).execute(createItemHandler(itemHandlerConsumer));
//        }

        if (listener instanceof ScenarioSourceListener) {
            Flux<Scenario> fluxScenarios = ((ScenarioSourceListener) listener).execute();
            fluxScenarios.subscribe(items -> {
            try {
                queue.put((T) items);
                System.out.println("scenario" + queue.size());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        } else {
            Flux<ProxyConfigHolder> fluxProxies = ((ProxySourcesClient) listener).execute();
            fluxProxies.subscribe(items -> {
                try {
                    queue.put((T) items);
                    System.out.println("proxy" + queue.size());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    private ProxyHandler createItemHandler(Consumer<ProxyConfigHolder> consumer) {
        return consumer::accept;
    }
}