package executor.service.service.impl.webDriver;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.service.WebDriverInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;

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
        ProxyCredentials proxyCredentials = new ProxyCredentials("ztzzilba", "l6q37njhw5s2");
        ProxyNetworkConfig proxyNetworkConfig = new ProxyNetworkConfig("185.199.229.156", 7492);
        proxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);
        webDriverInitializer = new WebDriverInitializerImpl();

        try {
            Field driverExecutable = webDriverInitializer.getClass().getDeclaredField("webDriverExecutable");
            Field chromeExecutable = webDriverInitializer.getClass().getDeclaredField("chromeExecutable");
            Field chromeVersion = webDriverInitializer.getClass().getDeclaredField("chromeVersion");
            Field pageLoadTimeout = webDriverInitializer.getClass().getDeclaredField("pageLoadTimeout");
            Field implicitlyWait = webDriverInitializer.getClass().getDeclaredField("implicitlyWait");

            driverExecutable.set(webDriverInitializer, System.getenv("DRIVER_EXECUTABLE"));
            chromeExecutable.set(webDriverInitializer, System.getenv("CHROME_EXECUTABLE"));
            chromeVersion.set(webDriverInitializer, "117.0.5938.88");
            pageLoadTimeout.set(webDriverInitializer, 30000L);
            implicitlyWait.set(webDriverInitializer, 30000L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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
        driver.get("https://www.ipaddress.my/");
        //WebElement element = driver.findElement(By.id("ip"));
        WebElement element = driver.findElement(By.cssSelector("li > span"));

        //assertEquals(element.getAttribute("value"), proxyConfigHolder.getProxyNetworkConfig().getHostname());
        assertEquals(element.getText(), proxyConfigHolder.getProxyNetworkConfig().getHostname());

    }

    @Test
    void testNoProxy() {
        proxyConfigHolder.getProxyNetworkConfig().setHostname(null);
        driver = webDriverInitializer.getInstance(proxyConfigHolder);
        driver.get("https://google.com");
        assertEquals(driver.getTitle(), "Google");
    }
}
