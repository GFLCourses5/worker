package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.service.ItemHandler;
import executor.service.service.Provider;
import executor.service.service.ProxySourcesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static executor.service.config.properties.PropertiesConstants.PROXY_CREDENTIAL;
import static executor.service.config.properties.PropertiesConstants.PROXY_NETWORK;

/**
 * Class for reading properties as JSON from properties file.
 *
 * @author Oleksandr Tuleninov, Nikita Hurmaza, Yurii Kotsiuba.
 * @version 01
 */
public class ProxySourcesClientImpl implements ProxySourcesClient {

    private static final Logger log = LoggerFactory.getLogger(ProxySourcesClientImpl.class);
    public static final int DELAY = 1;

    private Provider jsonReader;

    public ProxySourcesClientImpl() {
    }

    public ProxySourcesClientImpl(Provider jsonReader) {
        this.jsonReader = jsonReader;
    }

    @Override
    public void execute(ItemHandler handler) {
        List<ProxyConfigHolder> proxyConfigHoldersPrototypes = getListProxiesPrototypes();
        validateProxies(proxyConfigHoldersPrototypes);
        Flux<ProxyConfigHolder> proxiesFlux = getProxyFlux(proxyConfigHoldersPrototypes);
        proxiesFlux.subscribe(handler::onItemReceived);
    }

//    @Override
//    public Flux<ProxyConfigHolder> execute() {
//        List<ProxyConfigHolder> proxyConfigHoldersPrototypes = getListProxiesPrototypes();
//        validateProxies(proxyConfigHoldersPrototypes);
//        return getProxyFlux(proxyConfigHoldersPrototypes);
//    }

    /**
     * Check the list of ProxyConfigHolder entities.
     *
     * @param proxies list of ProxyConfigHolder entitie
     */
    private void validateProxies(List<ProxyConfigHolder> proxies) {
        if(proxies == null || proxies.isEmpty()) {
            log.error("Bad proxies list");
        }
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

    /**
     * Get the list with ProxyConfigHolder entities.
     *
     * @return list with ProxyConfigHolder entities
     */
    private List<ProxyConfigHolder> getListProxiesPrototypes() {
        List<ProxyNetworkConfig> proxyNetworkConfigs = jsonReader.provideData(PROXY_NETWORK, ProxyNetworkConfig.class);
        List<ProxyCredentials> proxyCredentials = jsonReader.provideData(PROXY_CREDENTIAL, ProxyCredentials.class);

        int numberOfIterations = Math.min(
                Objects.requireNonNull(proxyNetworkConfigs).size(),
                Objects.requireNonNull(proxyCredentials).size());

        List<ProxyConfigHolder> proxies = new ArrayList<>();
        for (int i = 0; i < numberOfIterations; i++) {
            ProxyConfigHolder proxy = createProxyConfigHolderPrototype(proxyNetworkConfigs, proxyCredentials, i);
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
