package executor.service.service;

import executor.service.model.Scenario;
import executor.service.model.response.ScenarioResult;
import executor.service.model.response.ScenarioResultResponse;

import java.util.List;
import java.util.Optional;

public interface ScenarioResultService {

    ScenarioResultResponse createScenarioResult(Scenario scenario);

    List<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer id);

    ScenarioResult findById(Integer id);
}
