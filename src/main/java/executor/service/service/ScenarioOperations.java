package executor.service.service;

import executor.service.model.request.ScenariosRequest;
import executor.service.model.response.ScenarioResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScenarioOperations {

    void execute(ScenariosRequest request);

    Page<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer userId, Pageable pageable);

    void deleteById(Integer resultId);

}
