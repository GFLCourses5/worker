package executor.service.service.impl.scenario;

import executor.service.model.request.Scenario;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for testing the functionality of the {@code ItemQueue} class.
 * This class contains unit tests to verify that {@code ItemQueue} is working correctly.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ScenarioSourceQueue
 * @see Scenario
 */
public class ScenarioSourceQueueTest {

    @Test
    void testPutAndGetScenario() {
        var scenario = new Scenario();
        var scenarios = List.of(scenario);

        var queue = new ScenarioSourceQueue();
        queue.addAllScenarios(scenarios);

        var result = queue.getScenario();

        assertEquals(scenario, result);
    }
}
