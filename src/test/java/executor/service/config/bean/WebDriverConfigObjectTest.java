package executor.service.config.bean;

import executor.service.config.properties.PropertiesConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Properties;

import static executor.service.config.properties.PropertiesConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for testing the functionality of the {@code WebDriverConfigObject} class.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see WebDriverConfigObject
 */
public class WebDriverConfigObjectTest {

    @Test
    public void testWebDriverConfig() {
        var propertiesConfig = Mockito.mock(PropertiesConfig.class);

        var properties = new Properties();
        properties.setProperty(WEB_DRIVER_EXECUTABLE, "/path/to/driver");
        properties.setProperty(USER_AGENT, "TestUserAgent");
        properties.setProperty(PAGE_LOAD_TIMEOUT, "5000");
        properties.setProperty(IMPLICITLY_WAIT, "2000");
        properties.setProperty(CHROME_EXECUTABLE, "/path/to/chrome");
        properties.setProperty(CHROME_VERSION, "89.0");

        when(propertiesConfig.getProperties("web-driver.properties")).thenReturn(properties);

        var configObject = new WebDriverConfigObject(propertiesConfig);
        var webDriverConfig = configObject.webDriverConfig();

        assertEquals("/path/to/driver", webDriverConfig.getWebDriverExecutable());
        assertEquals("TestUserAgent", webDriverConfig.getUserAgent());
        assertEquals(5000L, webDriverConfig.getPageLoadTimeout());
        assertEquals(2000L, webDriverConfig.getImplicitlyWait());
        assertEquals("/path/to/chrome", webDriverConfig.getChromeExecutable());
        assertEquals("89.0", webDriverConfig.getChromeVersion());
    }
}
