package executor.service.service.impl;

import executor.service.handler.ProxyHandler;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyNetworkConfig;
import executor.service.service.ProxySourcesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ProxySourcesClientImpl implements ProxySourcesClient {

    private static final int DELAY = 3;
    private static final Logger log = LoggerFactory.getLogger(ProxySourcesClient.class);

    @Override
    public void execute(ProxyHandler handler) {
        List<ProxyConfigHolder> proxyList = getProxyList();
        Flux<ProxyConfigHolder> proxyFlux = getProxyFlux(proxyList);
        proxyFlux.subscribe(handler::onProxyReceived);
    }

    //todo: replace file reading
    List<ProxyConfigHolder> getProxyList() {
        return Arrays.asList(
                new ProxyConfigHolder(new ProxyNetworkConfig("51.15.242.202", 8888), null),
                new ProxyConfigHolder(new ProxyNetworkConfig("45.201.134.38", 8080), null),
                new ProxyConfigHolder(new ProxyNetworkConfig("188.163.171.198", 8080), null)
        );
    }

    Flux<ProxyConfigHolder> getProxyFlux(List<ProxyConfigHolder> proxyList) {
        return Flux.fromIterable(proxyList)
                .log()
                .delayElements(Duration.ofSeconds(DELAY))
                .repeat();
    }
}