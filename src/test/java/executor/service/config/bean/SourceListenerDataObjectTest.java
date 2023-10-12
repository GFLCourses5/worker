package executor.service.config.bean;

import executor.service.config.properties.PropertiesConfig;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static executor.service.config.properties.PropertiesConstants.DELAY_PROXY_SECONDS;
import static executor.service.config.properties.PropertiesConstants.DELAY_SCENARIO_SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for testing the functionality of the {@code SourceListenerDataObject} class.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see SourceListenerDataObject
 */
public class SourceListenerDataObjectTest {

    @Test
    public void testSourceListenerData() {
        var properties = new Properties();
        properties.setProperty(DELAY_PROXY_SECONDS, "10");
        properties.setProperty(DELAY_SCENARIO_SECONDS, "20");

        var propertiesConfig = new PropertiesConfig() {
            @Override
            public Properties getProperties(String section) {
                return properties;
            }
        };

        var dataObject = new SourceListenerDataObject(propertiesConfig);
        var sourceListenerData = dataObject.sourceListenerData();

        assertEquals(10L, sourceListenerData.getDelayProxy());
        assertEquals(20L, sourceListenerData.getDelayScenario());
    }
}
