package executor.service.service.impl.webDriver;

import com.google.common.base.Strings;
import executor.service.model.ProxyConfigHolder;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Duration;

/**
 * The {@code WebDriverInitializerImpl} class is a factory that produces WebDriver instances.
 *
 * @author Oleksii Bondarenko
 * @version 01
 */
@Service
public class WebDriverInitializerImpl implements WebDriverInitializer {

    @Value("${driver.webDriverExecutable}")
    String webDriverExecutable;
    @Value("${driver.userAgent}")
    String userAgent;
    @Value("${driver.pageLoadTimeout}")
    Long pageLoadTimeout;
    @Value("${driver.implicitlyWait}")
    Long implicitlyWait;
    @Value("${chrome.executable}")
    String chromeExecutable;
    @Value("${chrome.version}")
    String chromeVersion;

    @Override
    public synchronized WebDriver getInstance(ProxyConfigHolder proxyConfigHolder) {
        String host = proxyConfigHolder.getProxyNetworkConfig().getHostname();
        Integer port = proxyConfigHolder.getProxyNetworkConfig().getPort();
        String username = proxyConfigHolder.getProxyCredentials().getUsername();
        String password = proxyConfigHolder.getProxyCredentials().getPassword();
        File proxyPlugin = null;

        ChromeOptions chromeOptions = configureChromeOptions();

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

        chromeOptions.setBinary(chromeExecutable);
        chromeOptions.setBrowserVersion(chromeVersion);
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("user-agent=" + userAgent);
        chromeOptions.setImplicitWaitTimeout(Duration.ofMillis(implicitlyWait));
        chromeOptions.setPageLoadTimeout(Duration.ofMillis(pageLoadTimeout));
        System.setProperty("webdriver.chrome.driver", webDriverExecutable);

        return chromeOptions;
    }
}

