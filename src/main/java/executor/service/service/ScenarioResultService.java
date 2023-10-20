package executor.service.service;

import executor.service.model.request.Scenario;
import executor.service.model.request.StepRequest;
import executor.service.model.response.ScenarioResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ScenarioResultService {

    void create(Scenario scenario, Map<StepRequest, Boolean> result);

    Page<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer userId, Pageable pageable);

    List<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer userId);

    void deleteById(Integer scenarioId);

}
