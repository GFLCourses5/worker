package executor.service.service.impl.proxy;

import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxySourceClient;
import executor.service.service.ProxyValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code ProxySourceQueueHandler} class is responsible for managing proxy sources
 * using the {@link ProxySourceClient} and a {@link ProxySourceQueue}.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class ProxySourceQueueHandler {

    private static List<ProxyConfigHolder> proxiesCache = new CopyOnWriteArrayList<>();
    @Value("${proxiesCache.size}")
    private int proxiesCacheSize;

    private final ProxySourceClient proxySourceClient;
    private final ProxySourceQueue queue;
    private final ProxyConfigHolder proxyConfigHolder;
    private final ProxyValidator validator;

    public ProxySourceQueueHandler(ProxySourceClient proxySourceClient,
                                   ProxySourceQueue queue,
                                   ProxyConfigHolder proxyConfigHolder,
                                   ProxyValidator validator) {
        this.proxySourceClient = proxySourceClient;
        this.queue = queue;
        this.proxyConfigHolder = proxyConfigHolder;
        this.validator = validator;
    }

    /**
     * Set a proxy into the queue.
     */
    public void setNoProxy(Integer sizeScenariosList) {
        while (sizeScenariosList > 0) {
            queue.putProxy(proxyConfigHolder);
            sizeScenariosList--;
        }
    }

    /**
     * Retrieve a proxy configuration from the queue.
     *
     * @return The ProxyConfigHolder object representing a proxy configuration.
     */
    public ProxyConfigHolder getProxy() {
        return queue.getProxy();
    }

    /**
     * Add a list of proxy configurations to the queue based on the size of the scenarios list.
     *
     * @param sizeScenariosList The size of the list of scenarios that determines how many proxy configurations to add.
     */
    public void addAllProxies(Integer sizeScenariosList) {
        proxiesCache = validation(proxiesCache);

        if (proxiesCache.size() <= proxiesCacheSize) {
            proxiesCache.addAll(proxySourceClient.execute());
        }

        int sizeProxiesList = proxiesCache.size();
        for (int i = 0; i < sizeScenariosList; i++) {
            int index = i % sizeProxiesList;
            queue.putProxy(proxiesCache.get(index));
        }
    }

    private List<ProxyConfigHolder> validation(List<ProxyConfigHolder> proxyList) {
        List<ProxyConfigHolder> validatedProxies = new ArrayList<>();
        for (ProxyConfigHolder proxy : proxyList) {
            if (validator.isValid(proxy)) {
                validatedProxies.add(proxy);
            }
        }
        return validatedProxies;
    }
}
