package executor.service.service.sources;

import executor.service.model.scenario.Scenario;
import reactor.core.publisher.Flux;

public interface ScenarioSourceListener {

    Flux<Scenario> getScenarios();

}
