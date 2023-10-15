package executor.service.controller;

import executor.service.model.Scenario;
import executor.service.model.request.ScenarioRequest;
import executor.service.model.response.ScenarioResponse;
import executor.service.service.SourceHandler;
import executor.service.service.impl.proxy.ProxySourceQueueHandler;
import executor.service.service.impl.scenario.ScenarioSourceQueueHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static executor.service.Routes.SCENARIOS;

/**
 * The {@code ScenarioSourceController} class represents a RESTful controller for handling scenarios and proxy configurations.
 * It provides endpoints for adding scenarios and proxy configurations to the respective queues.
 * <p>
 * This controller is accessible via the base path defined by {@link ScenarioSourceController}.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ScenarioSourceQueueHandler
 * @see ProxySourceQueueHandler
 * @see Scenario
 */
@RestController
@RequestMapping(value = SCENARIOS)
public class ScenarioSourceController {

    private final SourceHandler handler;

    public ScenarioSourceController(SourceHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ScenarioResponse>> getScenarioResult(@PathVariable Integer id) {
        return null; // todo
    }

    /**
     * Handles a POST request to process a scenario request.
     *
     * @param request The request containing a list of scenarios and a user ID to process.
     * @return A response entity with an HTTP status indicating success and an empty response body.
     */
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ScenarioResponse>> test(@RequestBody @Valid ScenarioRequest request) {
        handler.execute(request);
        return null;  // todo add service with ScenarioResponse from DB
    }
}
