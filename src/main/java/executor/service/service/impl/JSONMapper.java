package executor.service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.service.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Class for mapping properties as JSON from properties file.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class JSONMapper implements Provider {

    private static final Logger log = LoggerFactory.getLogger(ProxySourcesClientImpl.class);

    @Override
    public <T> List<T> provideData(String resource, Class<T> valueType) {
        return mapJsonFromPropertiesFile(resource, valueType);
    }

    /**
     * Get a List<T> from a properties file generated in JSON format.
     *
     * @param fileName properties file name
     * @return if reading successful - List<T> or if exception - null
     */
    private <T> List<T> mapJsonFromPropertiesFile(String fileName, Class<T> valueType) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
        } catch (IOException e) {
            log.error("Exception with parsing {} from resources file in the JSONMapper.class.", fileName, e);
            return null;
        }
    }
}
