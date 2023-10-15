package executor.service.config.bean;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Configuration class responsible for defining and configuring Spring beans.
 * <p>
 * This class contains methods annotated with {@code @Bean}, which define and configure
 * beans that can be managed by the Spring IoC container.
 * <p>
 * The {@code threadPoolExecutor} bean creates an {@link ExecutorService} based on the
 * specified properties, including core pool size, maximum pool size, and keep-alive time.
 * <p>
 * The {@code proxyConfigHolder} bean creates and configures a {@link ProxyConfigHolder} bean,
 * which is composed of a {@link ProxyNetworkConfig} and {@link ProxyCredentials}.
 *
 * @author Dima Silenko, Oleksandr Tuleninov
 * @version 01
 */
@Configuration
public class BeanConfiguration {

    /**
     * Create a bean of type {@link ExecutorService} using the specified properties.
     *
     * @param corePoolSize    The core size of the thread pool.
     * @param maximumPoolSize The maximum size of the thread pool.
     * @param keepAliveTime   The keep-alive time for idle threads.
     * @return An {@link ExecutorService} bean configured based on the provided properties.
     */
    @Bean
    public ExecutorService threadPoolExecutor(@Value("${pool.corePoolSize}") Integer corePoolSize,
                                              @Value("${pool.maximumPoolSize}") Integer maximumPoolSize,
                                              @Value("${pool.keepAliveTime}") Long keepAliveTime) {
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    /**
     * Create and configure a bean of type {@link ProxyConfigHolder}.
     *
     * @return A {@link ProxyConfigHolder} bean configured with a {@link ProxyNetworkConfig}
     * and {@link ProxyCredentials}.
     */
    @Bean
    public ProxyConfigHolder proxyConfigHolder() {
        return new ProxyConfigHolder(new ProxyNetworkConfig(), new ProxyCredentials());
    }
}
