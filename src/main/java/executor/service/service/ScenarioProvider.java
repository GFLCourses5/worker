package executor.service.service;


import executor.service.model.entity.Scenario;
import java.util.List;

public interface ScenarioProvider {

    List<Scenario> readScenarios();
}
