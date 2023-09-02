package executor.service.service.scenario;

import executor.service.model.entity.Scenario;
import reactor.core.publisher.Flux;

public interface ScenarioSourceListener {

  Flux<Scenario> getScenarios();

}
