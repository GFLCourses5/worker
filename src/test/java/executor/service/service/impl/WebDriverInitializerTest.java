package executor.service.service.impl;

import executor.service.config.properties.PropertiesConstants;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.model.WebDriverConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import static org.junit.jupiter.api.Assertions.*;

class WebDriverInitializerTest {
    ProxyConfigHolder proxyConfigHolder;
    WebDriverConfig webDriverConfig;
    WebDriver driver;

    @BeforeEach
    void setUp() {
        ProxyCredentials proxyCredentials = new ProxyCredentials("heix9999", "5ljkoauiuicw");
        ProxyNetworkConfig proxyNetworkConfig = new ProxyNetworkConfig("185.199.229.156", 7492);
        proxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);

        webDriverConfig = new WebDriverConfig(PropertiesConstants.WEB_DRIVER_EXECUTABLE, PropertiesConstants.USER_AGENT,
                30000L, 10000L);
    }

    @Test
    void testProxyConfigured() {
        WebDriverInitializerImpl webDriverInitializer = new WebDriverInitializerImpl();
        driver = webDriverInitializer.getInstance(webDriverConfig, proxyConfigHolder);
        driver.get("https://myip.com.ua/");



        WebElement element = driver.findElement(By.id("ip"));
        assertEquals(element.getAttribute("value"), proxyConfigHolder.getProxyNetworkConfig().getHostname());
    }

    @Test
    void testGetInstance() {
        WebDriverInitializerImpl webDriverInitializer = new WebDriverInitializerImpl();
        driver = webDriverInitializer.getInstance(webDriverConfig, proxyConfigHolder);
        assertInstanceOf(WebDriver.class, driver);
    }




    @AfterEach
    void tearDown() {
        //driver.quit();
    }
}