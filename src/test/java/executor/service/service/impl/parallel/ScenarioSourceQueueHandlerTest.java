package executor.service.service.impl.parallel;

import executor.service.model.request.Scenario;
import executor.service.service.impl.scenario.ScenarioSourceQueue;
import executor.service.service.impl.scenario.ScenarioSourceQueueHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for testing the functionality of the {@code ScenarioSourceQueueHandler} class.
 * This class contains unit tests to verify that {@code ScenarioSourceQueueHandler} is working correctly.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ScenarioSourceQueue
 * @see ScenarioSourceQueueHandler
 * @see Scenario
 */
public class ScenarioSourceQueueHandlerTest {

    private ScenarioSourceQueue queue;
    private ScenarioSourceQueueHandler handler;

    @BeforeEach
    public void setUp() {
        this.queue = Mockito.mock(ScenarioSourceQueue.class);
        this.handler = new ScenarioSourceQueueHandler(queue);
    }

    @Test
    public void testGetScenario() {
        Scenario scenario = new Scenario();

        Mockito.when(queue.getScenario()).thenReturn(scenario);

        Scenario retrievedScenario = handler.getScenario();
        assertEquals(scenario, retrievedScenario);
    }

    @Test
    public void testAddAllScenarios() {
        List<Scenario> scenarios = new ArrayList<>();
        scenarios.add(new Scenario());
        scenarios.add(new Scenario());

        handler.addAllScenarios(scenarios);

        Mockito.verify(queue).addAllScenarios(scenarios);
    }
}
