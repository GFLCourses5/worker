package executor.service.service.impl.parallel;

import executor.service.model.Scenario;
import executor.service.service.ItemHandler;
import executor.service.service.ScenarioSourceListener;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * The {@code ScenarioTaskWorker} class represents a worker task that retrieves
 * and processes scenario configurations from a {@link ScenarioSourceListener}.
 * It implements the {@link Callable} interface to perform its work asynchronously.
 * <p>
 * This class is responsible for executing the {@link ScenarioSourceListener} to fetch
 * proxy configurations and then stores them in a {@link ScenarioSourceQueue}.
 * The retrieved scenario configuration is returned as the result of the call.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ScenarioSourceListener
 * @see ScenarioSourceQueue
 * @see ItemHandler
 */
public class ScenarioTaskWorker implements Callable<Scenario> {

    private final ScenarioSourceListener listener;
    private final ScenarioSourceQueue queue;

    public ScenarioTaskWorker(ScenarioSourceListener listener,
                              ScenarioSourceQueue queue) {
        this.listener = listener;
        this.queue = queue;
    }

    @Override
    public Scenario call() {
        Consumer<Scenario> itemHandlerConsumer = queue::putScenario;

        listener.execute(createHandler(itemHandlerConsumer));

        return queue.getScenario();
    }

    private ItemHandler<Scenario> createHandler(Consumer<Scenario> consumer) {
        return consumer::accept;
    }
}
