package executor.service.service;

import executor.service.model.request.ScenarioRequest;

public interface SourceHandler {

    void execute(ScenarioRequest request);

}
