package executor.service.service.impl.listener;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import executor.service.model.Scenario;
import executor.service.service.ScenarioProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * The {@code JSONFileScenarioProvider} class implements the {@link ScenarioProvider} interface
 * that reads scenarios from a JSON file.
 * <p>
 *
 * @author Yurii Kotsiuba, Oleksandr Tuleninov, Kostia Hromovii.
 * @version 01
 * @see executor.service.service.ScenarioProvider
 * @see executor.service.config.properties.PropertiesConfig
 */
@Service
public class JSONFileScenarioProvider implements ScenarioProvider {

    private static final Logger log = LoggerFactory.getLogger(ScenarioProvider.class);

    /**
     * Reads and parses scenarios from a JSON file.
     *
     * @param fileName The name of the JSON file to read scenarios from.
     * @return A list of {@link Scenario} entities read from the JSON file.
     */
    @Override
    public List<Scenario> readScenarios(String fileName) {
        List<Scenario> scenarios = null;
        try (InputStream inputStream = getClass().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            scenarios = parseScenariosFromJson(reader);
        } catch (IOException e) {
            log.error("Cannot read file {}. Reason: {}", fileName, e.getMessage(), e);
        }
        return scenarios;
    }

    /**
     * Parses a list of scenarios from a JSON file.
     *
     * @param reader The {@link BufferedReader} used to read the JSON data.
     * @return A list of {@link Scenario} entities parsed from the JSON data.
     */
    private List<Scenario> parseScenariosFromJson(BufferedReader reader) {
        Type listType = new TypeToken<List<Scenario>>() {
        }.getType();
        return new Gson().fromJson(reader, listType);
    }
}
