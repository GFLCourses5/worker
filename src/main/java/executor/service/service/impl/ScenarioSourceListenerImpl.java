package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ScenarioSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

import static executor.service.config.properties.PropertiesConstants.SCENARIOS;

/**
 * Class for application`s constants.
 *
 * @author Yurii Kotsiuba, Oleksandr Tuleninov
 * @version 01
 * */
public class ScenarioSourceListenerImpl<T> implements ScenarioSourceListener<T> {

    private static final Logger log = LoggerFactory.getLogger(ScenarioSourceListener.class);
    public static final int DELAY = 1;

    private final Provider jsonReader;

    public ScenarioSourceListenerImpl(Provider jsonReader) {
        this.jsonReader = jsonReader;
    }

    @Override
    public void execute(T handler) {
        List<Scenario> scenariosPrototypes = getListProxiesPrototypes();
        validateScenarios(scenariosPrototypes);
        Flux<ProxyConfigHolder> proxiesFlux = getScenarioFlux(scenariosPrototypes);
        proxiesFlux.subscribe(((ItemHandler<ProxyConfigHolder>)handler)::onItemReceived);
    }

    /**
     * Check the list of ProxyConfigHolder entities.
     *
     * @param scenarios list of Scenario entities
     */
    private void validateScenarios(List<Scenario> scenarios) {
        if(scenarios == null || scenarios.isEmpty()) {
            log.error("Bad scenarios list");
        }
    }

    /**
     * Get Flux with Scenario entities continuously.
     *
     * @return list with Scenario entities
     */
    private Flux<Scenario> getScenarioFlux(List<Scenario> proxies) {
        return Flux.fromIterable(proxies)
                .log()
                .delayElements(Duration.ofSeconds(DELAY))
                .repeat();
    }

    /**
     * Get the list with Scenario entities.
     *
     * @return list with Scenario entities
     */
    private List<Scenario> getListProxiesPrototypes() {
        return jsonReader.provideData(SCENARIOS, ScenarioSourceListener.class);
    }
}
