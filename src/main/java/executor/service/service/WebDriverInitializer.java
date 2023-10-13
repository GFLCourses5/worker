package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import org.openqa.selenium.WebDriver;

public interface WebDriverInitializer {
    WebDriver getInstance(ProxyConfigHolder proxyConfigHolder);
}
