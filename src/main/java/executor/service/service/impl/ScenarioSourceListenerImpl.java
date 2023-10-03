package executor.service.service.impl;

import executor.service.config.properties.PropertiesConfig;
import executor.service.config.properties.PropertiesConstants;
import executor.service.model.Scenario;
import executor.service.service.ItemHandler;
import executor.service.service.ScenarioProvider;
import executor.service.service.ScenarioSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ScenarioSourceListenerImpl implements ScenarioSourceListener {
    private final ScenarioProvider provider;
    private final Properties properties;
    private static final Logger log = LoggerFactory.getLogger(ScenarioSourceListener.class);

    public ScenarioSourceListenerImpl(ScenarioProvider provider, PropertiesConfig propertiesConfig) {
        this.provider = provider;
        this.properties = propertiesConfig.getProperties(PropertiesConstants.SOURCES_PROPERTIES);
        System.out.println();
    }

    @Override
    public void execute(ItemHandler handler) {
        List<Scenario> scenarios = provider.readScenarios();
        validateScenarios(scenarios);
        Flux<Scenario> scenariosFlux = getScenarioFlux(scenarios);
        scenariosFlux.subscribe(handler::onItemReceived);
    }

    /**
     * Check the list of ProxyConfigHolder entities.
     *
     * @param scenarios list of Scenario entities
     */
    private void validateScenarios(List<Scenario> scenarios) {
        if (scenarios == null || scenarios.isEmpty()) {
            log.error("The scenarios list is bad.");
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
                .delayElements(Duration.ofSeconds(getDelay()))
                .repeat();
    }

    private Long getDelay() {
        return Long.parseLong(properties.getProperty(PropertiesConstants.DELAY_SECONDS));
    }
}
