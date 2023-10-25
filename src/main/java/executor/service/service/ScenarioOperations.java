package executor.service.service;

import executor.service.model.request.ScenariosRequest;
import executor.service.model.response.ScenarioResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ScenarioOperations {

    void execute(ScenariosRequest request);

    Page<ScenarioResultResponse> getAllScenarioResultsByUserId(Long userId, Pageable pageable);

    List<ScenarioResultResponse> getAllScenarioResultsByUserId(Long userId);

    Optional<ScenarioResultResponse> getScenarioResultById(Long id);

    void deleteById(Long resultId, Long userId);

}
