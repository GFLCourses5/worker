package executor.service.model.request;

import executor.service.model.Scenario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Represents a request containing a list of scenarios and a user ID.
 * This class is used for input validation and data transfer.
 *
 * @author Oleksii Bondarenko
 * @version 01
 * @see Scenario
 */
public record ScenarioRequest(

        @Valid
        List<Scenario> scenarios,

        @NotNull(message = "user id must not be null")
        @Min(value = 0, message = "userId must be greater than or equal to 0")
        Integer userId,

        @NotNull(message = "proxy required must not be null")
        Boolean proxyRequired

) {
}
