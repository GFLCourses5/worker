package executor.service.service;

import executor.service.model.Scenario;

public interface ScenarioHandler {

    void onScenarioReceived(Scenario item);

}
