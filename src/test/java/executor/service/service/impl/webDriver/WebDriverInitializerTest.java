package executor.service.service.impl.webDriver;

import executor.service.config.bean.WebDriverConfigObject;
import executor.service.config.properties.PropertiesConfig;
import executor.service.config.properties.PropertiesConstants;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.model.WebDriverConfig;
import executor.service.service.WebDriverInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Class for testing the functionality of the {@code WebDriverInitializerImpl}.
 *
 *  @author  Oleksii Bondarenko
 *  @version 0.1
 * */

class WebDriverInitializerTest {
    private ProxyConfigHolder proxyConfigHolder;
    private WebDriver driver;
    private WebDriverInitializer webDriverInitializer;

    @BeforeEach
    void setUp() {
        ProxyCredentials proxyCredentials = new ProxyCredentials("heix9999", "5ljkoauiuicw");
        ProxyNetworkConfig proxyNetworkConfig = new ProxyNetworkConfig("185.199.229.156", 7492);
        proxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.getProperties(PropertiesConstants.WEB_DRIVER);
        /*webDriverConfig = new WebDriverConfig(properties.getProperty(PropertiesConstants.WEB_DRIVER_EXECUTABLE),
                properties.getProperty(PropertiesConstants.USER_AGENT),
                60000L, 60000L, "path_to_chrome", "chrome_version");*/
        WebDriverConfig webDriverConfig = new WebDriverConfigObject(new PropertiesConfig()).webDriverConfig();
        webDriverInitializer = new WebDriverInitializerImpl(webDriverConfig);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void testGetInstance() {
        driver = webDriverInitializer.getInstance(proxyConfigHolder);
        assertInstanceOf(WebDriver.class, driver);
    }

    @Test
    void testProxyConfigured() {
        driver = webDriverInitializer.getInstance(proxyConfigHolder);
        driver.get("https://myip.com.ua/");
        WebElement element = driver.findElement(By.id("ip"));
        assertEquals(element.getAttribute("value"), proxyConfigHolder.getProxyNetworkConfig().getHostname());
    }

    @Test
    void testNoProxy() {
        proxyConfigHolder.getProxyNetworkConfig().setHostname(null);
        driver = webDriverInitializer.getInstance(proxyConfigHolder);
        driver.get("https://google.com");
        assertEquals(driver.getTitle(), "Google");
    }
}
