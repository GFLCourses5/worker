package executor.service.service.impl;

import executor.service.config.properties.PropertiesConfig;
import executor.service.config.properties.PropertiesConstants;
import executor.service.model.ProxyConfigHolder;
import executor.service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ProxySourcesClientImpl implements ProxySourcesClient {
    private static final Logger log = LoggerFactory.getLogger(ProxySourcesClientImpl.class);
    private final ProxyProvider provider;
    private final ProxyValidator proxyValidator;
    private final Properties properties;

    public ProxySourcesClientImpl(ProxyProvider provider,
                                  ProxyValidator proxyValidator, PropertiesConfig propertiesConfig) {
        this.provider = provider;
        this.proxyValidator = proxyValidator;
        this.properties = propertiesConfig.getProperties(PropertiesConstants.SOURCES_PROPERTIES);
    }

    @Override
    public void execute(ItemHandler handler) {
        List<ProxyConfigHolder> proxyConfigHolders = provider.readProxy(PropertiesConstants.PROXIES);
        List<ProxyConfigHolder> proxies = validateProxies(proxyConfigHolders);
        Flux<ProxyConfigHolder> proxiesFlux = getProxyFlux(proxies);
        proxiesFlux.subscribe(proxy -> {
            if (proxyValidator.isValid(proxy)) {
                handler.onItemReceived(proxy);
            }
        });
    }

    /**
     * Check the list of ProxyConfigHolder entities.
     *
     * @param proxies list of ProxyConfigHolder entitie
     */
    private List<ProxyConfigHolder> validateProxies(List<ProxyConfigHolder> proxies) {
        if (proxies.isEmpty()) {
            log.error("The proxies list is bad.");
        }
        return proxies;
    }

    /**
     * Get Flux with ProxyConfigHolder entities continuously.
     *
     * @return list with ProxyConfigHolder entities
     */
    private Flux<ProxyConfigHolder> getProxyFlux(List<ProxyConfigHolder> proxies) {
        return Flux.fromIterable(proxies)
                .log()
                .delayElements(Duration.ofSeconds(getDelay()))
                .repeat();
    }

    private Long getDelay() {
        return Long.parseLong(properties.getProperty(PropertiesConstants.DELAY_SECONDS));
    }
}
