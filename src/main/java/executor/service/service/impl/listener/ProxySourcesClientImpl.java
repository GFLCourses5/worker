package executor.service.service.impl.listener;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ProxyConfigHolder;
import executor.service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static executor.service.config.properties.PropertiesConstants.*;

/**
 * The {@code ProxySourcesClientImpl} class implements the {@link ProxySourcesClient} interface
 * that reads scenarios from a {@link ScenarioProvider}
 * and emits them as a {@link Flux} stream with a specified delay.
 * <p>
 *
 * @author Oleksandr Tuleninov, NikitaHurmaza, Yurii Kotsiuba, Oleksii Bondarenko, Dima Silenko
 * @version 01
 * @see ScenarioProvider
 * @see PropertiesConfig
 */
@Service
public class ProxySourcesClientImpl implements ProxySourcesClient {
    private static final Logger log = LoggerFactory.getLogger(ProxySourcesClientImpl.class);
    private final ProxyProvider provider;
    private final ProxyValidator proxyValidator;
    private final PropertiesConfig propertiesConfig;

    public ProxySourcesClientImpl(ProxyProvider provider,
                                  ProxyValidator proxyValidator,
                                  PropertiesConfig propertiesConfig) {
        this.provider = provider;
        this.proxyValidator = proxyValidator;
        this.propertiesConfig = propertiesConfig;
    }

    /**
     * Executes the proxy sources client by reading proxy configurations from the {@link ProxyProvider},
     * validating them, and emitting valid proxies as a continuous stream using a {@link Flux}.
     *
     * @param handler The {@link ItemHandler} to handle received proxy configurations.
     */
    @Override
    public void execute(ItemHandler handler) {
        List<ProxyConfigHolder> proxyConfigHolders = provider.readProxy(FILE_NAME_PROXIES);
        List<ProxyConfigHolder> proxies = validateProxies(proxyConfigHolders);
        Flux<ProxyConfigHolder> proxiesFlux = getProxyFlux(proxies);
        proxiesFlux.subscribe(proxy -> {
            if (proxyValidator.isValid(proxy)) {
                handler.onItemReceived(proxy);
            }
        });
    }

    /**
     * Validates the list of proxy configurations to ensure it is not empty.
     *
     * @param proxies The list of {@link ProxyConfigHolder} entities to validate.
     * @return The validated list of proxy configurations.
     */
    private List<ProxyConfigHolder> validateProxies(List<ProxyConfigHolder> proxies) {
        if (proxies.isEmpty()) {
            log.error("The proxies list is bad.");
        }
        return proxies;
    }

    /**
     * Retrieves a {@link Flux} stream with {@link ProxyConfigHolder} entities continuously,
     * applying a delay between emissions.
     *
     * @param proxies The list of {@link ProxyConfigHolder} entities to emit.
     * @return A {@link Flux} stream emitting proxy configurations with the specified delay.
     */
    private Flux<ProxyConfigHolder> getProxyFlux(List<ProxyConfigHolder> proxies) {
        return Flux.fromIterable(proxies)
                .log()
                .delayElements(Duration.ofSeconds(getDelay()))
                .repeat();
    }

    private Long getDelay() {
        Properties properties = propertiesConfig.getProperties(SOURCES_PROPERTIES);
        return Long.parseLong(properties.getProperty(DELAY_PROXY_SECONDS));
    }
}