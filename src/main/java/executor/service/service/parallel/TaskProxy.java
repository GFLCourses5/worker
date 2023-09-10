package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxySourcesClient;
import reactor.core.publisher.Flux;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class TaskProxy implements Runnable {

    private final ProxySourcesClient listener;
    private final BlockingQueue<ProxyConfigHolder> queue;

    public TaskProxy(ProxySourcesClient listener, BlockingQueue<ProxyConfigHolder> queue) {
        this.listener = listener;
        this.queue = queue;
    }

    @Override
    public void run() {
        Flux<ProxyConfigHolder> fluxProxy = listener.execute();
        fluxProxy.subscribe(item -> {
            try {
                queue.put(item);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println("proxy: " + queue.size());
    }
}
