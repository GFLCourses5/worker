package executor.service.service.impl.listener;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code JSONFileProxyProvider} class implements the {@link ProxyProvider} interface
 * that reads proxies from a JSON file.
 * <p>
 *
 * @author Yurii Kotsiuba, Oleksandr Tuleninov, Dima Silenko.
 * @version 01
 * @see ProxyProvider
 * @see executor.service.config.properties.PropertiesConfig
 */
@Service
public class JSONFileProxyProvider implements ProxyProvider {

    private static final Logger log = LoggerFactory.getLogger(JSONFileProxyProvider.class);

    /**
     * Reads and parses proxies from a JSON file.
     *
     * @param fileName The name of the JSON file to read proxies from.
     * @return A list of {@link ProxyConfigHolder} entities read from the JSON file.
     */
    @Override
    public List<ProxyConfigHolder> readProxy(String fileName) {
        List<ProxyConfigHolder> proxies = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            proxies = parseScenariosFromJson(reader);
        } catch (IOException e) {
            log.error("Cannot read file {}. Reason: {}", fileName, e.getMessage(), e);
        }
        return proxies;
    }

    /**
     * Parses a list of proxies from a JSON file.
     *
     * @param reader The {@link BufferedReader} used to read the JSON data.
     * @return A list of {@link ProxyConfigHolder} entities parsed from the JSON data.
     */
    private List<ProxyConfigHolder> parseScenariosFromJson(BufferedReader reader) {
        Type listType = new TypeToken<List<ProxyConfigHolder>>() {}.getType();
        return new Gson().fromJson(reader, listType);
    }
}
