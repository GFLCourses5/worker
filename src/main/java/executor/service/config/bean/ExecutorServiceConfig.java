package executor.service.config.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Configuration class responsible for defining and configuring the thread pool executor bean.
 * <p>
 * This class contains a method annotated with {@code @Bean} that defines and configures
 * a bean for an {@link ExecutorService} based on the specified properties, including core
 * pool size, maximum pool size, and keep-alive time.
 * <p>
 *
 * @author Dima Silenko, Oleksandr Tuleninov
 * @version 01
 * @see ThreadPoolExecutor
 */
@Configuration
public class ExecutorServiceConfig {

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
}
