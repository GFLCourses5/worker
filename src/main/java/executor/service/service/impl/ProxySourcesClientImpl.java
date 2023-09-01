package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.service.Provider;
import executor.service.service.ProxySourcesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static executor.service.config.properties.PropertiesConstants.PROXY_CREDENTIALS;
import static executor.service.config.properties.PropertiesConstants.PROXY_NETWORK_CONFIG;

/**
 * Class for reading properties as JSON from properties file.
 *
 * @author Oleksandr Tuleninov, Nikita Hurmaza
 * @version 01
 */
public class ProxySourcesClientImpl implements ProxySourcesClient {

    private static final Logger log = LoggerFactory.getLogger(ProxySourcesClientImpl.class);
    public static final int DELAY = 1;

    private final Provider jsonReader;

    public ProxySourcesClientImpl(Provider jsonReader) {
        this.jsonReader = jsonReader;
    }

    /**
     * Get list with ProxyConfigHolder entities continuously.
     *
     * @return list with ProxyConfigHolder entities
     */
    @Override
    public Flux<ProxyConfigHolder> getProxies() {
        var proxies = getListProxiesPrototype();
        validateProxies(proxies);
        return Flux.fromIterable(proxies)
                .log()
                .delayElements(Duration.ofSeconds(DELAY))
                .repeat();
    }

    /**
     * Check the list of ProxyConfigHolder entities.
     *
     * @param proxies list of ProxyConfigHolder entitie
     */
    private void validateProxies(List<ProxyConfigHolder> proxies) {
        if (proxies == null || proxies.isEmpty()) {
            log.error("List proxies cannot be null or empty");
            throw new IllegalArgumentException("List cannot be null or empty");
        }
    }

    /**
     * Get the list with ProxyConfigHolder entities.
     *
     * @return list with ProxyConfigHolder entities
     */
    private List<ProxyConfigHolder> getListProxiesPrototype() {
        var proxyNetworkConfigs = jsonReader.provideData(PROXY_NETWORK_CONFIG, ProxyNetworkConfig.class);
        var proxyCredentials = jsonReader.provideData(PROXY_CREDENTIALS, ProxyCredentials.class);

        int numberOfIterations = Math.min(
                Objects.requireNonNull(proxyNetworkConfigs).size(),
                Objects.requireNonNull(proxyCredentials).size());

        List<ProxyConfigHolder> proxies = new ArrayList<>();
        for (int i = 0; i < numberOfIterations; i++) {
            var proxy = createProxyConfigHolderPrototype(proxyNetworkConfigs, proxyCredentials, i);
            proxies.add(proxy);
        }

        return proxies;
    }

    /**
     * Create ProxyConfigHolder entity.
     *
     * @param proxyNetworkConfigs list with ProxyNetworkConfig entity
     * @param proxyCredentials    list with ProxyCredentials entity
     * @param i                   element number
     * @return ProxyConfigHolder entity
     */
    private ProxyConfigHolder createProxyConfigHolderPrototype(List<ProxyNetworkConfig> proxyNetworkConfigs,
                                                               List<ProxyCredentials> proxyCredentials,
                                                               int i) {
        return new ProxyConfigHolder(
                proxyNetworkConfigs.get(i),
                proxyCredentials.get(i));
    }
}
