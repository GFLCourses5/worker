package executor.service.service;

import executor.service.model.response.ScenarioResultResponse;

import java.util.List;

public interface ScenarioResultService {

    List<ScenarioResultResponse> getAllScenarioResultsByUserId(Integer id);

}
