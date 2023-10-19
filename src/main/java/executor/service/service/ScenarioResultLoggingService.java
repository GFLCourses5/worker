package executor.service.service;

import executor.service.model.request.Scenario;
import executor.service.model.request.StepRequest;
import executor.service.model.response.ScenarioResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ScenarioResultLoggingService {

    void create(Scenario scenario, Map<StepRequest, Boolean> result);

    Page<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer id, Pageable pageable);

    void deleteById(Integer scenarioId);

}
