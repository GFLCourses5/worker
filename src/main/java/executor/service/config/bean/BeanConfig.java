package executor.service.config.bean;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.model.ThreadPoolConfig;
import executor.service.service.Provider;

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
     * Create ThreadPoolConfig bean from properties file.
     * */
    public ThreadPoolConfig threadPoolConfig() {
        var properties = propertiesConfig.getProperties(THREAD_POOL_PROPERTIES);
        var corePoolSize = Integer.parseInt(properties.getProperty(CORE_POOL_SIZE));
        var keepAliveTime = Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME));

        var threadPoolConfig = new ThreadPoolConfig();
        threadPoolConfig.setCorePoolSize(corePoolSize);
        threadPoolConfig.setKeepAliveTime(keepAliveTime);

        return threadPoolConfig;
    }

    /**
     * Create ThreadPoolExecutor bean from properties file.
     * */
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfig threadPoolConfig) {
        int MAXIMUM_POOL_SIZE = 5;
        return new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                MAXIMUM_POOL_SIZE,
                threadPoolConfig.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    /**
     * Create ProxyConfigHolder bean from properties file.
     * */
    public ProxyConfigHolder proxyConfigHolder() {
        var proxyNetworkConfigs = jsonReader.provideData(PROXY_NETWORK_DEFAULT, ProxyNetworkConfig.class);
        var proxyCredentials = jsonReader.provideData(PROXY_CREDENTIALS_DEFAULT, ProxyCredentials.class);

        return new ProxyConfigHolder(proxyNetworkConfigs.get(0), proxyCredentials.get(0));
    }
}
