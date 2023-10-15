package executor.service.model.response;

import executor.service.model.ScenarioResult;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * A class representing a response for scenarios.
 *
 * @author Oleksii Bondarenko
 * @version 01
 */
public record ScenarioResponse(Integer id,
                               String name,
                               String site,
                               List<String> stepsResults,
                               OffsetDateTime executedAt) {

    public static ScenarioResponse formScenarioResult(ScenarioResult result) {
        return new ScenarioResponse(
                result.getId(),
                result.getName(),
                result.getSite(),
                result.getStepsResults(),
                result.getExecutedAt()
        );
    }
}
