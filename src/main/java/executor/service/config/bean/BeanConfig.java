package executor.service.config.bean;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.*;
import executor.service.service.Provider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static executor.service.config.properties.PropertiesConstants.*;

/**
 * Class for creating beans from properties file.
 *
 *  @author Oleksandr Tuleninov
 *  @version 01
 * */
public class BeanConfig {

    private final PropertiesConfig propertiesConfig;
    private final Provider jsonReader;


    public BeanConfig(PropertiesConfig propertiesConfig, Provider jsonReader) {
        this.propertiesConfig = propertiesConfig;
        this.jsonReader = jsonReader;
    }

    /**
     * Create a ThreadPoolExecutor bean from properties file.
     * */
    public ExecutorService threadPoolExecutor() {
        ThreadPoolConfig threadPoolConfig = threadPoolConfig();
        int MAXIMUM_POOL_SIZE = 5;
        return new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                MAXIMUM_POOL_SIZE,
                threadPoolConfig.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    /**
     * Create a ThreadPoolConfig bean from properties file.
     * */
    private ThreadPoolConfig threadPoolConfig() {
        var properties = propertiesConfig.getProperties(THREAD_POOL_PROPERTIES);
        var corePoolSize = Integer.parseInt(properties.getProperty(CORE_POOL_SIZE));
        var keepAliveTime = Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME));

        return new ThreadPoolConfig(corePoolSize, keepAliveTime);
    }

    /**
     * Create a default ProxyConfigHolder bean from properties file.
     * */
    public ProxyConfigHolder proxyConfigHolderDefault() {
        var proxyNetworkConfigs = jsonReader.provideData(PROXY_NETWORK_DEFAULT, ProxyNetworkConfig.class);
        var proxyCredentials = jsonReader.provideData(PROXY_CREDENTIALS_DEFAULT, ProxyCredentials.class);

        return new ProxyConfigHolder(proxyNetworkConfigs.get(0), proxyCredentials.get(0));
    }

    /**
     * Create a default WebDriverConfig bean from properties file.
     * */
    public WebDriverConfig webDriverConfig() {
        var properties = propertiesConfig.getProperties(WEB_DRIVER);
        var webDriverExecutable = properties.getProperty(WEB_DRIVER_EXECUTABLE);
        var userAgent = properties.getProperty(USER_AGENT);
        var pageLoadTimeout = Long.parseLong(properties.getProperty(PAGE_LOAD_TIMEOUT));
        var implicitlyWait = Long.parseLong(properties.getProperty(IMPLICITLY_WAIT));

        return new WebDriverConfig(webDriverExecutable, userAgent, pageLoadTimeout, implicitlyWait);
    }
}
