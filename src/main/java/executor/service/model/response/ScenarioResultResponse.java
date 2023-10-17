package executor.service.model.response;

import java.time.OffsetDateTime;
import java.util.Set;

/**
 * A class representing a response for scenarios.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record ScenarioResultResponse(Integer id,
                                     String name,
                                     String site,
                                     Set<StepResult> stepsResults,
                                     OffsetDateTime executedAt) {

    public static ScenarioResultResponse formScenarioResult(ScenarioResult result) {
        return new ScenarioResultResponse(
                result.getId(),
                result.getName(),
                result.getSite(),
                result.getStepsResults(),
                result.getExecutedAt()
        );
    }
}
