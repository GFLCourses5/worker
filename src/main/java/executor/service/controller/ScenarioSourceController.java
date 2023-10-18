package executor.service.controller;

import executor.service.model.request.ScenarioRequest;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.service.ScenarioResultLoggingService;
import executor.service.service.SourceHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static executor.service.Routes.SCENARIOS;

/**
 * The {@code ScenarioSourceController} class
 * represents a REST controller responsible for handling scenario-related requests and results.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see SourceHandler
 * @see ScenarioResultLoggingService
 * @see ScenarioRequest
 * @see ScenarioResultResponse
 */
@RestController
@RequestMapping(value = SCENARIOS)
public class ScenarioSourceController {

    private final SourceHandler handler;
    private final ScenarioResultLoggingService scenarioResultLoggingService;

    public ScenarioSourceController(SourceHandler handler,
                                    ScenarioResultLoggingService scenarioResultService) {
        this.handler = handler;
        this.scenarioResultLoggingService = scenarioResultService;
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
    public void receiveScenarios(@RequestBody @Valid ScenarioRequest request) {
        handler.execute(request);
    }

    /**
     * Handles a GET request to retrieve scenario results for a specific user.
     *
     * @param userId The identifier of the user for whom scenario results are requested.
     * @return A response entity with a list of scenario result responses.
     */
    @GetMapping(
            value = "/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ScenarioResultResponse>> getScenariosResult(@PathVariable Integer userId) {
        return ResponseEntity.ok()
                .body(scenarioResultLoggingService.getAllScenarioResultsByUserId(userId));
    }

    /**
     * Handles a DELETE request to delete a specific scenario result by its identifier.
     *
     * @param resultId The identifier of the scenario result to be deleted.
     */
    @DeleteMapping(value = "/{resultId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteResultById(@PathVariable Integer resultId) {
        scenarioResultLoggingService.deleteById(resultId);
    }
}