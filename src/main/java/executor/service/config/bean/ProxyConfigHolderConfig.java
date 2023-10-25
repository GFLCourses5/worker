package executor.service.config.bean;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class responsible for defining and configuring the {@link ProxyConfigHolder} bean.
 * <p>
 * This class contains a method annotated with {@code @Bean} that creates and configures
 * a {@link ProxyConfigHolder} bean, which is composed of a {@link ProxyNetworkConfig} and
 * {@link ProxyCredentials}. The configured bean can be used to manage proxy configuration
 * and credentials for network communication.
 *
 * @author Dima Silenko, Oleksandr Tuleninov
 * @version 01
 */
@Configuration
public class ProxyConfigHolderConfig {


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
