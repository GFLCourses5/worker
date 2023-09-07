package executor.service.service.scenario;

import executor.service.model.scenario.Scenario;

import java.util.List;

public interface ScenarioProvider {

    List<Scenario> readScenarios();
}
