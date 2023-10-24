package executor.service.model.request;

import jakarta.validation.constraints.NotBlank;

public record StepRequest(

        @NotBlank(message = "step action must not be null or empty")
        String action,
        @NotBlank(message = "step value must not be null or empty")
        String value

) {
}
