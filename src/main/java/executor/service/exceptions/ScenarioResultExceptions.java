package executor.service.exceptions;

import executor.service.model.ScenarioResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Utility class for creating exception instances related to {@link ScenarioResult}.
 *
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ResponseStatusException
 */
public class ScenarioResultExceptions {

    public ScenarioResultExceptions() {
    }

    public static ResponseStatusException scenarioNotFound(Long scenarioId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Scenario result with id " + scenarioId + " not found");
    }
}
