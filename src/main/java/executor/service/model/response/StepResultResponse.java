package executor.service.model.response;

import executor.service.model.Step;

/**
 * A class representing a response for step.
 *
 * @author Oleksandr Tuleninov
 * @version 01
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
