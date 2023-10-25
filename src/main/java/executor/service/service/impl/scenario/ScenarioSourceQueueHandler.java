package executor.service.service.impl.scenario;

import executor.service.model.request.Scenario;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The {@code ScenarioSourceQueueHandler} class is designed
 * to handle scenarios using the {@link ScenarioSourceQueue}.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class ScenarioSourceQueueHandler {

    private final ScenarioSourceQueue queue;

    public ScenarioSourceQueueHandler(ScenarioSourceQueue queue) {
        this.queue = queue;
    }

    /**
     * Get a scenario from the queue.
     *
     * @return The scenario retrieved from the queue.
     */
    public Scenario getScenario() {
        return queue.getScenario();
    }

    /**
     * Add a list of scenarios to the queue.
     *
     * @param scenarios The list of scenarios to add to the queue.
     */
    public void addAllScenarios(List<Scenario> scenarios) {
        queue.addAllScenarios(scenarios);
    }
}
