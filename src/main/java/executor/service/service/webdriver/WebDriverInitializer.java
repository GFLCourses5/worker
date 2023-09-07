package executor.service.service.webdriver;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.config.WebDriverConfig;
import org.openqa.selenium.WebDriver;

public interface WebDriverInitializer {
    WebDriver getInstance(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder);
}
