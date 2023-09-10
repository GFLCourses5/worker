package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ScenarioSourceListener;
import reactor.core.publisher.Flux;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class TaskScenario implements Runnable {

    private final ScenarioSourceListener listener;
    private final BlockingQueue<Scenario> queue;

    public TaskScenario(ScenarioSourceListener listener, BlockingQueue<Scenario> queue) {
        this.listener = listener;
        this.queue = queue;
    }

    @Override
    public void run() {
        Flux<Scenario> fluxProxy = listener.execute();
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

        System.out.println("scenario: " + queue.size());
    }
}
