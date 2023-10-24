package executor.service.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for testing the functionality of the {@code ScenarioResultExceptionsTest}.
 * This class contains unit tests to verify that {@code ScenarioResultExceptionsTest} is working correctly.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ScenarioResultExceptionsTest {

    @Test
    public void testScenarioNotFound() {
        Integer scenarioId = 1;
        ResponseStatusException exception = ScenarioResultExceptions.scenarioNotFound(scenarioId);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(Objects.requireNonNull(
                exception.getReason()).contains("Scenario result with id " + scenarioId + " not found"));
    }
}
