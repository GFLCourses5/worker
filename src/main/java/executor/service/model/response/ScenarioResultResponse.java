package executor.service.model.response;

import executor.service.model.ScenarioResult;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * A class representing a response for scenarios.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record ScenarioResultResponse(Integer id,
                                     String name,
                                     String site,
                                     List<StepResultResponse> stepsResults,
                                     OffsetDateTime executedAt) {

    public static ScenarioResultResponse formScenarioResult(ScenarioResult result) {
        var stepResultResponses = result.getStepsResults().stream()
                .map(StepResultResponse::fromStepResult)
                .toList();

        return new ScenarioResultResponse(
                result.getId(),
                result.getName(),
                result.getSite(),
                stepResultResponses,
                result.getExecutedAt()
        );
    }
}
