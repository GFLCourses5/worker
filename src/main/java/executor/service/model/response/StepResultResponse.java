package executor.service.model.response;

import executor.service.model.Step;
import executor.service.model.StepResult;

/**
 * A class representing a response for step.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see Step
 */
public record StepResultResponse(Step step,
                                 Boolean isPassed) {

    public static StepResultResponse fromStepResult(StepResult result) {
        return new StepResultResponse(
                result.getStep(),
                result.getIsPassed()
        );
    }
}
