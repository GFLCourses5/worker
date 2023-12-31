package executor.service.controller;

import executor.service.exceptions.ScenarioResultExceptions;
import executor.service.model.request.ScenariosRequest;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.service.ScenarioOperations;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static executor.service.Routes.SCENARIOS;

/**
 * The {@code ScenarioSourceController} class
 * represents a REST controller responsible for handling scenario-related requests and results.
 * This controller provides endpoints to process scenario requests, retrieve scenario results for
 * specific users, and delete specific scenario results by their identifier.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ScenarioOperations
 * @see ScenariosRequest
 * @see ScenarioResultResponse
 * @see Page
 * @see Pageable
 */
@RestController
@RequestMapping(value = SCENARIOS)
public class ScenarioSourceController {

    private final ScenarioOperations scenarioOperations;

    public ScenarioSourceController(ScenarioOperations scenarioOperations) {
        this.scenarioOperations = scenarioOperations;
    }

    /**
     * Handles a POST request to process a scenario request.
     *
     * @param request The request containing a list of scenarios and a user ID to process.
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public void receiveScenarios(@RequestBody @Valid ScenariosRequest request) {
        scenarioOperations.execute(request);
    }

    /**
     * Handles a GET request to retrieve scenario results for a specific user.
     *
     * @param userId The identifier of the user for whom scenario results are requested.
     * @return A response entity with a list of scenario result responses.
     */
    @GetMapping(
            value = "/{userId}/page",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Page<ScenarioResultResponse> getScenariosResult(@PathVariable Long userId, Pageable pageable) {
        return scenarioOperations.getAllScenarioResultsByUserId(userId, pageable);
    }

    /**
     * Handles a GET request to retrieve scenario results for a specific user.
     *
     * @param userId The identifier of the user for whom scenario results are requested.
     * @return A response entity with a list of scenario result responses.
     */
    @GetMapping(
            value = "/user/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ScenarioResultResponse> getScenariosResult(@PathVariable Long userId) {
        return scenarioOperations.getAllScenarioResultsByUserId(userId);
    }

    /**
     * Handles a GET request to retrieve a specific scenario result by id.
     *
     * @param id The identifier of scenario result is requested.
     * @return A response entity.
     */
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ScenarioResultResponse getScenarioById(@PathVariable Long id) {
        return scenarioOperations.getScenarioResultById(id)
                .orElseThrow(() -> ScenarioResultExceptions.scenarioNotFound(id));
    }

    /**
     * Handles a DELETE request to delete a specific scenario result by its identifier.
     *
     * @param resultId The identifier of the scenario result to be deleted.
     */
    @DeleteMapping(value = "/{resultId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteResultById(@PathVariable(value = "resultId") Long resultId,
                                 @RequestParam(value = "userId") Long userId) {
        scenarioOperations.deleteById(resultId, userId);
    }
}
