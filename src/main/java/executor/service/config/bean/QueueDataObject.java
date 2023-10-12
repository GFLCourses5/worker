package executor.service.config.bean;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.QueueData;

import static executor.service.config.properties.PropertiesConstants.QUEUE_CAPACITY;
import static executor.service.config.properties.PropertiesConstants.QUEUE_PROPERTIES;

/**
 * The {@code QueueDataObject} class is responsible for creating a {@link QueueData}
 * bean from properties provided by the {@link PropertiesConfig}.
 *
 * @author Oleksandr Tuleninov.
 * @version 01
 * @see PropertiesConfig
 * @see QueueData
 */
public class QueueDataObject {

    private final PropertiesConfig propertiesConfig;

    public QueueDataObject(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    /**
     * Create a default QueueData bean from properties file.
     */
    public QueueData queueData() {
        var properties = propertiesConfig.getProperties(QUEUE_PROPERTIES);
        var capacity = Integer.parseInt(properties.getProperty(QUEUE_CAPACITY));

        return new QueueData(capacity);
    }
}
