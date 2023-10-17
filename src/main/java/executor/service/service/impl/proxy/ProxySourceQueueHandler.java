package executor.service.service.impl.proxy;

import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxySourceClient;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private final ProxySourceClient proxySourceClient;
    private final ProxySourceQueue queue;
    private final ProxyConfigHolder proxyConfigHolder;

    public ProxySourceQueueHandler(ProxySourceClient proxySourceClient,
                                   ProxySourceQueue queue,
                                   ProxyConfigHolder proxyConfigHolder) {
        this.proxySourceClient = proxySourceClient;
        this.queue = queue;
        this.proxyConfigHolder = proxyConfigHolder;
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
        List<ProxyConfigHolder> proxies = proxySourceClient.execute();
        int sizeProxiesList = proxies.size();

        for (int i = 0; i < sizeScenariosList; i++) {
            int index = i % sizeProxiesList;
            queue.putProxy(proxies.get(index));
        }
    }
}
