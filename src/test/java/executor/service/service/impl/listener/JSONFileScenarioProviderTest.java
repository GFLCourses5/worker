package executor.service.service.impl.listener;

import executor.service.model.Scenario;
import executor.service.service.ScenarioProvider;
import org.junit.jupiter.api.Test;

import java.util.List;

import static executor.service.config.properties.PropertiesConstants.FILE_NAME_SCENARIOS;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for testing the functionality of the {@code JSONFileScenarioProvider} class
 * is an implementation of the {@link ScenarioProvider}.
 * This class contains unit tests to verify that {@code JSONFileScenarioProvider} is working correctly.
 * <p>
 *
 * @author Kostia Hromovii, Yurii Kotsiuba.
 * @version 01
 * @see executor.service.service.ScenarioProvider
 * @see executor.service.service.impl.listener.JSONFileScenarioProvider
 * @see executor.service.model.Scenario
 */
public class JSONFileScenarioProviderTest {

    @Test
    void testReadScenarios_type() {
        ScenarioProvider provider = new JSONFileScenarioProvider();
        List<Scenario> scenarios = provider.readScenarios(FILE_NAME_SCENARIOS);

        for(Scenario scenario: scenarios){
            assertEquals(Scenario.class, scenario.getClass());
        }
    }
}
