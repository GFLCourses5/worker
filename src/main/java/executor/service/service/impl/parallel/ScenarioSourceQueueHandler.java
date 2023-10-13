package executor.service.service.impl.parallel;

import executor.service.model.Scenario;
import executor.service.service.ItemHandler;
import executor.service.service.ScenarioSourceListener;

import java.util.function.Consumer;

/**
 * The {@code ScenarioTaskWorker} class represents a worker task that retrieves
 * and processes scenario configurations from a {@link ScenarioSourceListener}.
 * It implements the {@link Runnable} interface to perform its work asynchronously.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ScenarioSourceQueue
 * @see ItemHandler
 * @see Consumer
 */
public class ScenarioSourceQueueHandler implements Runnable {

    private final ScenarioSourceListener listener;
    private final ScenarioSourceQueue queue;

    public ScenarioSourceQueueHandler(ScenarioSourceListener listener,
                                      ScenarioSourceQueue queue) {
        this.listener = listener;
        this.queue = queue;
    }

    public Scenario getScenario() {
        return queue.getScenario();
    }

    @Override
    public void run() {
        Consumer<Scenario> itemHandlerConsumer = queue::putScenario;

        listener.execute(createHandler(itemHandlerConsumer));
    }

    private ItemHandler<Scenario> createHandler(Consumer<Scenario> consumer) {
        return consumer::accept;
    }
}
