package executor.service.service.sources;


import executor.service.model.scenario.Scenario;

public interface ScenarioHandler {
    void onScenarioReceived(Scenario scenario);
}
