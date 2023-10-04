package executor.service.webdriver;

import executor.service.model.configs.WebDriverConfig;
import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.proxy.ProxyCredentials;
import executor.service.model.proxy.ProxyNetworkConfig;
import executor.service.service.webdriver.WebDriverInitializer;
import executor.service.service.webdriver.WebDriverInitializerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        WebDriverConfig webDriverConfig = new WebDriverConfig(System.getenv("CHROME_PATH"),
                "user-agent",
                60000L,
                60000L);
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
