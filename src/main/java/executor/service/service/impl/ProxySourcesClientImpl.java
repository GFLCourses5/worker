package executor.service.service.impl;

import executor.service.config.properties.PropertiesConstants;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyNetworkConfig;
import executor.service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class ProxySourcesClientImpl implements ProxySourcesClient {

    private static final Logger log = LoggerFactory.getLogger(ProxySourcesClientImpl.class);
    public static final int DELAY = 1;

    private final ProxyProvider provider;
    private final ProxyValidator proxyValidator;
    private final ProxyConfigHolder defaultProxy;

    public ProxySourcesClientImpl(ProxyProvider provider,
                                  ProxyValidator proxyValidator,
                                  ProxyConfigHolder defaultProxy) {
        this.provider = provider;
        this.proxyValidator = proxyValidator;
        this.defaultProxy = defaultProxy;
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
                .delayElements(Duration.ofSeconds(DELAY))
                .repeat();
    }
}
