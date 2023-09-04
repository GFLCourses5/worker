package executor.service.service.parallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Queue;

public class TaskWorker<T> implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(TaskWorker.class);

    private final Flux<T> flux;
    private final Queue<T> queue;

    public TaskWorker(Flux<T> flux, Queue<T> queue) {
        this.flux = flux;
        this.queue = queue;
    }

    @Override
    public void run() {
        flux.subscribe(queue::add);
        threadSleep();
    }

    /**
     * Wait for the Flux thread to start executing.
     */
    private void threadSleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.info("Thread was interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
