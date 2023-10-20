package executor.service.service.impl;

import executor.service.model.request.ScenariosRequest;
import executor.service.model.response.ScenarioResultResponse;
import executor.service.service.ScenarioOperations;
import executor.service.service.ScenarioResultService;
import executor.service.service.SourceHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * The {@code ScenarioService} class is a service responsible for executing and managing scenarios.
 * <p>
 * This service implements the {@link ScenarioOperations} interface and provides methods to execute
 * scenario requests, retrieve scenario results for specific users, and delete specific scenario
 * results by their identifier.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see SourceHandler
 * @see ScenarioResultService
 * @see ScenariosRequest
 * @see ScenarioResultResponse
 * @see Page
 * @see Pageable
 */
@Service
public class ScenarioService implements ScenarioOperations {

    private final SourceHandler sourceHandler;
    private final ScenarioResultService scenarioResultService;

    public ScenarioService(SourceHandler sourceHandler,
                           ScenarioResultService scenarioResultService) {
        this.sourceHandler = sourceHandler;
        this.scenarioResultService = scenarioResultService;
    }

    /**
     * Executes a list of scenarios for a given user based on the provided request.
     *
     * @param request The request containing a list of scenarios and a user ID to process.
     */
    @Override
    public void execute(ScenariosRequest request) {
        sourceHandler.execute(request);
    }

    /**
     * Retrieves a page of scenario results for a specific user.
     *
     * @param userId   The identifier of the user for whom scenario results are requested.
     * @param pageable The pagination information for the results.
     * @return A page of scenario result responses.
     */
    @Override
    public Page<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer userId, Pageable pageable) {
        return scenarioResultService.getAllScenarioResultsByUserId(userId, pageable);
    }

    /**
     * Deletes a specific scenario result by its identifier.
     *
     * @param resultId The identifier of the scenario result to be deleted.
     */
    @Override
    public void deleteById(Integer resultId) {
        scenarioResultService.deleteById(resultId);
    }
}
