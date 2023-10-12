package executor.service.config.bean;

import executor.service.config.properties.PropertiesConfig;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static executor.service.config.properties.PropertiesConstants.QUEUE_CAPACITY;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for testing the functionality of the {@code QueueDataObjectTest} class.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see QueueDataObject
 */
public class QueueDataObjectTest {

    @Test
    public void testSourceListenerData() {
        var properties = new Properties();
        properties.setProperty(QUEUE_CAPACITY, "5");

        var propertiesConfig = new PropertiesConfig() {
            @Override
            public Properties getProperties(String section) {
                return properties;
            }
        };

        var dataObject = new QueueDataObject(propertiesConfig);
        var queueData = dataObject.queueData();

        assertEquals(5, queueData.getCapacity());
    }
}
