package executor.service.config.bean;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ThreadPoolConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static executor.service.config.properties.PropertiesConstants.*;
import static executor.service.config.properties.PropertiesConstants.KEEP_ALIVE_TIME;

/**
 * The {@code ThreadPoolExecutor} class is responsible for creating an {@link ExecutorService}
 * using the configuration properties provided by the {@link PropertiesConfig}.
 * It allows for the dynamic creation of a thread pool executor based on specified properties.
 *
 * @author Dima Silenko, Oleksandr Tuleninov.
 * @version 01
 * @see PropertiesConfig
 * @see ThreadPoolConfig
 */
public class ThreadPoolExecutor {

    private final PropertiesConfig propertiesConfig;

    public ThreadPoolExecutor(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    /**
     * Create a ThreadPoolExecutor bean from properties file.
     * */
    public ExecutorService threadPoolExecutor() {
        ThreadPoolConfig threadPoolConfig = threadPoolConfig();
        var properties = propertiesConfig.getProperties(THREAD_POOL_PROPERTIES);
        int size = Integer.parseInt(properties.getProperty(MAXIMUM_POOL_SIZE));
        return new java.util.concurrent.ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                size,
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
}
