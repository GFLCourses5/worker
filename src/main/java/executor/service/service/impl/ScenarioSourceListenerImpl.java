package executor.service.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import executor.service.model.Scenario;
import executor.service.service.ItemHandler;
import executor.service.service.Provider;
import executor.service.service.ScenarioSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;

import static executor.service.config.properties.PropertiesConstants.SCENARIOS;

/**
 * Class for application`s constants.
 *
 * @author Yurii Kotsiuba
 * @version 01
 * */
public class ScenarioSourceListenerImpl<T> implements ScenarioSourceListener<T> {

    private static final Logger log = LoggerFactory.getLogger(ScenarioSourceListenerImpl.class);
    public static final int DELAY = 1;

    private Provider jsonReader;

    public ScenarioSourceListenerImpl() {
    }

    public ScenarioSourceListenerImpl(Provider jsonReader) {
        this.jsonReader = jsonReader;
    }

    @Override
    public void execute(T handler) {
        List<Scenario> scenariosPrototypes = getListScenariosPrototypes();
        validateScenarios(scenariosPrototypes);
        Flux<Scenario> scenariosFlux = getScenarioFlux(scenariosPrototypes);
        scenariosFlux.subscribe(((ItemHandler<Scenario>)handler)::onItemReceived);
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
    private List<Scenario> getListScenariosPrototypes() {
        //return jsonReader.provideData(SCENARIOS, Scenario.class);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("scenarios.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructType(List.class);
            return objectMapper.readValue(inputStream, javaType);
        } catch (IOException e) {
            log.error("Exception with parsing {} from resources file in the JSONReader.class.", "scenarios.json", e);
            return null;
        }

    }
}
