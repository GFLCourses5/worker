package executor.service.config.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The {@code ThreadPoolExecutor} class is responsible for creating an {@link ExecutorService}.
 * It allows for the dynamic creation of a thread pool executor based on specified properties.
 *
 * @author Dima Silenko, Oleksandr Tuleninov
 * @version 01
 */
@Configuration
public class ThreadPoolExecutorConfig {

    /**
     * Create a ExecutorService bean from properties file.
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
