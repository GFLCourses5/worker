package executor.service.service.impl.scenario;

import executor.service.model.request.Scenario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@code ScenarioSourceQueue} class represents a thread-safe queue for storing
 * and retrieving instances of {@link Scenario}.
 * It provides methods for putting and getting scenario configurations.
 * <p>
 * This class uses a blocking queue internally to ensure thread safety when
 * adding and retrieving scenario configurations.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see BlockingQueue
 * @see InterruptedException
 * @see RuntimeException
 */
@Component
public class ScenarioSourceQueue {
    private final BlockingQueue<Scenario> queue;

    public ScenarioSourceQueue() {
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * Put a scenario into the queue.
     *
     * @param scenario The scenario to be added to the queue.
     */
    public void putScenario(Scenario scenario) {
        try {
            queue.put(scenario);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a scenario from the queue.
     *
     * @return The scenario retrieved from the queue.
     */
    public Scenario getScenario() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add a list of scenarios to the queue.
     *
     * @param scenarios The list of scenarios to add to the queue.
     */
    public void addAllScenarios(List<Scenario> scenarios) {
        queue.addAll(scenarios);
    }
}
