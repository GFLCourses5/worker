package executor.service.service.impl;

import executor.service.handler.ScenarioHandler;
import executor.service.model.Scenario;
import executor.service.service.ScenarioProvider;
import executor.service.service.ScenarioSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class ScenarioSourceListenerImpl implements ScenarioSourceListener {

    private static final long DELAY = 1;
    private final ScenarioProvider provider;

    private static final Logger log = LoggerFactory.getLogger(ScenarioSourceListener.class);

    public ScenarioSourceListenerImpl(ScenarioProvider provider) {
        this.provider = provider;
    }

    @Override
    public void execute(ScenarioHandler handler) {
        List<Scenario> scenarios = provider.readScenarios();
        validateScenarios(scenarios);
        Flux<Scenario> scenariosFlux = getScenarioFlux(scenarios);
        scenariosFlux.subscribe(handler::onScenarioReceived);
    }

    /**
     * Check the list of ProxyConfigHolder entities.
     *
     * @param scenarios list of Scenario entities
     */
    private void validateScenarios(List<Scenario> scenarios) {
        if (scenarios == null || scenarios.isEmpty()) {
            log.error("Bad scenarios list");
        }
    }

    /**
     * Get Flux with Scenario entities continuously.
     *
     * @return list with Scenario entities
     */
    private Flux<Scenario> getScenarioFlux(List<Scenario> scenarios) {
        return Flux.fromIterable(scenarios)
                .log()
                .delayElements(Duration.ofSeconds(DELAY))
                .repeat();
    }
}
