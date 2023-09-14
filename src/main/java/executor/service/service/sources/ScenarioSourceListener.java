package executor.service.service.sources;

import executor.service.handler.ProxyHandler;
import executor.service.model.scenario.Scenario;
import reactor.core.publisher.Flux;

public interface ScenarioSourceListener {

    Flux<Scenario> getScenarios();

  interface ProxySourcesClient {

    void execute(ProxyHandler handler);

  }
}
