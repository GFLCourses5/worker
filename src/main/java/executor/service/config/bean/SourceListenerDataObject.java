package executor.service.config.bean;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.SourceListenerData;

import static executor.service.config.properties.PropertiesConstants.*;

/**
 * The {@code SourceListenerDataObject} class is responsible for creating a {@link SourceListenerData}
 * bean from properties provided by the {@link PropertiesConfig}.
 *
 * @author Oleksandr Tuleninov.
 * @version 01
 * @see PropertiesConfig
 * @see SourceListenerData
 */
public class SourceListenerDataObject {

    private final PropertiesConfig propertiesConfig;

    public SourceListenerDataObject(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    /**
     * Create a default SourceListenerData bean from properties file.
     */
    public SourceListenerData sourceListenerData() {
        var properties = propertiesConfig.getProperties(SOURCES_PROPERTIES);
        var delayPoxy = Long.parseLong(properties.getProperty(DELAY_PROXY_SECONDS));
        var delayScenario = Long.parseLong(properties.getProperty(DELAY_SCENARIO_SECONDS));

        return new SourceListenerData(delayPoxy, delayScenario);
    }
}
