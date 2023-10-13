package executor.service.service.impl.parallel;

import executor.service.model.Scenario;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

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
public class ScenarioSourceQueue {
    private final BlockingQueue<Scenario> queue;

    public ScenarioSourceQueue() {
        this.queue = new LinkedBlockingDeque<>();
    }

    public void putScenario(Scenario scenario) {
        try {
            queue.put(scenario);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Scenario getScenario() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
