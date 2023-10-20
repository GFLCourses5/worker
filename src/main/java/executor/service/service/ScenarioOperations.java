package executor.service.service;

import executor.service.model.request.ScenariosRequest;
import executor.service.model.response.ScenarioResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScenarioOperations {

    void execute(ScenariosRequest request);

    Page<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer userId, Pageable pageable);

    List<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer userId);

    void deleteById(Integer resultId);

}
