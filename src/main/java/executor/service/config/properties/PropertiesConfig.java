package executor.service.config.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

/**
 * Retrieves properties from a resource file with the specified file name.
 * This method loads properties from a resource file located on the classpath
 * and returns them as a {@link Properties} object.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see Properties
 * @see UncheckedIOException
 */
public class PropertiesConfig {

    private static final Logger log = LoggerFactory.getLogger(PropertiesConfig.class);

    /**
     * Get the properties from resources file.
     *
     * @param fileName The name of the properties file with its extension
     * @return A {@link Properties} object representing a persistent set of properties loaded
     * from the specified resource file
     * @throws UncheckedIOException If an I/O error occurs while trying to load the properties from the resource file
     */
    public Properties getProperties(String fileName) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(fileName)) {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            log.info("UncheckedIOException in the PropertiesConfig.class.");
            throw new UncheckedIOException(e);
        }
    }
}
