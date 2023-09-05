package executor.service.service.parallel;

import reactor.core.publisher.Flux;

import java.util.Queue;

public class TaskWorker<T> implements Runnable {

    private final Flux<T> flux;
    private final Queue<T> queue;

    public TaskWorker(Flux<T> flux, Queue<T> queue) {
        this.flux = flux;
        this.queue = queue;
    }

    @Override
    public void run() {
        flux.subscribe(queue::add);
    }
}
