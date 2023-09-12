package executor.service.config;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ThreadPoolConfig;

import static executor.service.config.properties.PropertiesConstants.*;

public class ThreadPoolConfigFactory {
    private static ThreadPoolConfig poolConfig;

    public static ThreadPoolConfig getPoolConfig() {
        if (poolConfig == null) {
            poolConfig = configureThreadPoolConfig();
        }
        return poolConfig;
    }

    private static ThreadPoolConfig configureThreadPoolConfig() {
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        var properties = propertiesConfig.getProperties(THREAD_POOL_PROPERTIES);
        var corePoolSize = Integer.parseInt(properties.getProperty(CORE_POOL_SIZE));
        var keepAliveTime = Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME));
        ThreadPoolConfig poolConfig = new ThreadPoolConfig();
        poolConfig.setCorePoolSize(corePoolSize);
        poolConfig.setKeepAliveTime(keepAliveTime);

        return poolConfig;
    }

}
