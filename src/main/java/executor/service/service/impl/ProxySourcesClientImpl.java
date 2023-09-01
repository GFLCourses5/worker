package executor.service.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.service.ProxySourcesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static executor.service.config.properties.PropertiesConstants.PROXY_CREDENTIALS;
import static executor.service.config.properties.PropertiesConstants.PROXY_NETWORK_CONFIG;

public class ProxySourcesClientImpl implements ProxySourcesClient {

    private static final Logger log = LoggerFactory.getLogger(ProxySourcesClientImpl.class);
    public static final int DELAY = 1;

    @Override
    public Flux<ProxyConfigHolder> getProxies() {
        List<ProxyConfigHolder> proxyConfigHoldersPrototype = getProxyConfigHoldersPrototype();
        validateProxies(proxyConfigHoldersPrototype);
        return Flux.fromIterable(proxyConfigHoldersPrototype)
                .log()
                .delayElements(Duration.ofSeconds(DELAY))
                .repeat();
    }

    private void validateProxies(List<ProxyConfigHolder> proxies) {
        if(proxies == null || proxies.isEmpty()) {
            log.error("Bad proxies list");
            throw new IllegalArgumentException("List cannot be null or empty");
        }
    }

    private List<ProxyConfigHolder> getProxyConfigHoldersPrototype() {
        List<ProxyCredentials> proxyCredentials = readProxyCredentialsFromJson();
        List<ProxyNetworkConfig> proxyNetworkConfigs = readProxyNetworkConfigFromJson();

        int numberOfIterations = Math.min(
                Objects.requireNonNull(proxyCredentials).size(),
                Objects.requireNonNull(proxyNetworkConfigs).size());

        List<ProxyConfigHolder> proxyConfigHolders = new ArrayList<>();
        for (int i = 0; i < numberOfIterations; i++) {
            ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();
            proxyConfigHolder.setProxyCredentials(proxyCredentials.get(i));
            proxyConfigHolder.setProxyNetworkConfig(proxyNetworkConfigs.get(i));
            proxyConfigHolders.add(proxyConfigHolder);
        }

        return proxyConfigHolders;
    }

    private List<ProxyCredentials> readProxyCredentialsFromJson() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROXY_CREDENTIALS)) {
            return new ObjectMapper().readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            log.info("""
                    Exception with parsing proxy-credentials.json from resources file in the ProxySourcesClientImpl.class.
                    """);
            return null;
        }
    }

    private List<ProxyNetworkConfig> readProxyNetworkConfigFromJson() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROXY_NETWORK_CONFIG)) {
            return new ObjectMapper().readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            log.info("""
                    Exception with parsing proxy-network.json from resources file in the ProxySourcesClientImpl.class.
                    """);
            return null;
        }
    }
}
