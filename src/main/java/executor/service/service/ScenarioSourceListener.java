package executor.service.service;

import executor.service.model.Scenario;
import reactor.core.publisher.Flux;

public interface ScenarioSourceListener {

    //void execute(ScenarioHandler handler);
    Flux<Scenario> execute();

}
