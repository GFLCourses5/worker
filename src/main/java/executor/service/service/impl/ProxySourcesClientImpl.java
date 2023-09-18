package executor.service.service.impl;

import executor.service.handler.ProxyHandler;
import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxyProvider;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ProxyValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProxySourcesClientImpl implements ProxySourcesClient {

    private static final Logger log = LoggerFactory.getLogger(ProxySourcesClient.class);
    private static final long DELAY = 1000;
    private final ProxyProvider provider;
    private final ProxyValidator validator;


    public ProxySourcesClientImpl(ProxyProvider provider, ProxyValidator validator) {
        this.provider = provider;
        this.validator = validator;
    }


    @Override
    public void execute(ProxyHandler handler) {
        List<ProxyConfigHolder> proxies = provider.readProxy();
        validateList(proxies);
        listen(proxies, handler);
    }

    private void validateList(List<ProxyConfigHolder> proxies) {
        if(proxies == null || proxies.isEmpty()) {
            log.error("Bad proxy list.");
            throw new IllegalArgumentException("List cannot be null or empty");
        }
    }
    private void listen(List<ProxyConfigHolder> proxies, ProxyHandler handler) {
        int currentIndex = 0;
        while(true){
            ProxyConfigHolder proxy = proxies.get(currentIndex);
            if(validator.isValid(proxy)) {
                handler.onProxyReceived(proxy);
            }
            currentIndex = (currentIndex + 1) % proxies.size();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}