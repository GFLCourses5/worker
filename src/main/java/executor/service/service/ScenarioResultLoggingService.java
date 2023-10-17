package executor.service.service;

import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.model.response.ScenarioResultResponse;

import java.util.List;
import java.util.Map;

public interface ScenarioResultLoggingService {

    void create(Scenario scenario, Map<Step, Boolean> result);

    List<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer id);

    void deleteById(Integer scenarioId);

}
