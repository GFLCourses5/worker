package executor.service.service.impl.webDriver;

import com.google.common.base.Strings;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.WebDriverConfig;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.Duration;

/**
 * The {@code WebDriverInitializerImpl} class is a factory that produces WebDriver instances.
 *
 * @author Oleksii Bondarenko
 * @version 01
 */
public class WebDriverInitializerImpl implements WebDriverInitializer {
    private final WebDriverConfig webDriverConfig;
    private final ChromeOptions chromeOptions;

    public WebDriverInitializerImpl(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
        this.chromeOptions = configureChromeOptions();
    }

    @Override
    public synchronized WebDriver getInstance(ProxyConfigHolder proxyConfigHolder) {
        String host = proxyConfigHolder.getProxyNetworkConfig().getHostname();
        Integer port = proxyConfigHolder.getProxyNetworkConfig().getPort();
        String username = proxyConfigHolder.getProxyCredentials().getUsername();
        String password = proxyConfigHolder.getProxyCredentials().getPassword();
        File proxyPlugin = null;

        if (!Strings.isNullOrEmpty(host)) {
            if (!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password)) {
                proxyPlugin = ChromeProxyPlugin.generate(host, port, username, password);
                chromeOptions.addExtensions(proxyPlugin);
            } else {
                chromeOptions.addArguments("--proxy-server=" + host + ":" + port);
            }
        }

        ChromeDriver driver = new ChromeDriver(chromeOptions);
        if (proxyPlugin != null) {
            proxyPlugin.delete();
        }

        return driver;
    }

    private ChromeOptions configureChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setBinary(webDriverConfig.getChromeExecutable());
        chromeOptions.setBrowserVersion(webDriverConfig.getChromeVersion());
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("user-agent=" + webDriverConfig.getUserAgent());
        chromeOptions.setImplicitWaitTimeout(Duration.ofMillis(webDriverConfig.getImplicitlyWait()));
        chromeOptions.setPageLoadTimeout(Duration.ofMillis(webDriverConfig.getPageLoadTimeout()));
        System.setProperty("webdriver.chrome.driver", webDriverConfig.getWebDriverExecutable());

        return chromeOptions;
    }
}

