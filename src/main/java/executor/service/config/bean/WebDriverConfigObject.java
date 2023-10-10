package executor.service.config.bean;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.WebDriverConfig;

import static executor.service.config.properties.PropertiesConstants.*;

/**
 * The {@code WebDriverConfigObject} class is responsible for creating a {@link WebDriverConfig}
 * bean from properties provided by the {@link PropertiesConfig}.
 * It allows for the creation of a WebDriver configuration object with default settings based on specified properties.
 *
 * @author Dima Silenko, Oleksandr Tuleninov.
 * @version 01
 * @see PropertiesConfig
 * @see WebDriverConfig
 */
public class WebDriverConfigObject {

    private final PropertiesConfig propertiesConfig;

    public WebDriverConfigObject(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    /**
     * Create a default WebDriverConfig bean from properties file.
     * */
    public WebDriverConfig webDriverConfig() {
        var properties = propertiesConfig.getProperties(WEB_DRIVER);
        var webDriverExecutable = properties.getProperty(WEB_DRIVER_EXECUTABLE);
        var userAgent = properties.getProperty(USER_AGENT);
        var pageLoadTimeout = Long.parseLong(properties.getProperty(PAGE_LOAD_TIMEOUT));
        var implicitlyWait = Long.parseLong(properties.getProperty(IMPLICITLY_WAIT));

        return new WebDriverConfig(webDriverExecutable, userAgent, pageLoadTimeout, implicitlyWait);
    }
}
