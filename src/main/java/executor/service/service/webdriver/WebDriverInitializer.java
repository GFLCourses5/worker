package executor.service.service.webdriver;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.config.WebDriverConfig;
import org.openqa.selenium.WebDriver;

public class WebDriverInitializer {
    private WebDriverConfig webDriverConfig;
    private ProxyConfigHolder proxyConfigHolder;
    private WebDriver driver;

    public WebDriverInitializer(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
        this.webDriverConfig = webDriverConfig;
        this.proxyConfigHolder = proxyConfigHolder;
    }

    public WebDriver getInstance() {
        return driver;
    }
}
