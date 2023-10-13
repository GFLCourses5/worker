package executor.service.service.impl.listener;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.SourceListenerData;
import executor.service.service.ItemHandler;
import executor.service.service.ProxyProvider;
import executor.service.service.ProxySourceClient;
import executor.service.service.ProxyValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

import static executor.service.config.properties.PropertiesConstants.FILE_NAME_PROXIES;

/**
 * The {@code ProxySourcesClientImpl} class implements the {@link ProxySourceClient} interface
 * that reads scenarios from a {@link ProxyProvider}
 * and emits them as a {@link Flux} stream with a specified delay.
 * <p>
 *
 * @author Oleksandr Tuleninov, NikitaHurmaza, Yurii Kotsiuba, Oleksii Bondarenko, Dima Silenko
 * @version 01
 * @see ProxyValidator
 * @see SourceListenerData
 * @see ItemHandler
 * @see ProxyConfigHolder
 */
@Service
public class ProxySourceClientImpl implements ProxySourceClient {

    private static final Logger log = LoggerFactory.getLogger(ProxySourceClientImpl.class);

    private final ProxyProvider provider;
    private final ProxyValidator proxyValidator;
    private final SourceListenerData data;

    public ProxySourceClientImpl(ProxyProvider provider,
                                 ProxyValidator proxyValidator,
                                 SourceListenerData data) {
        this.provider = provider;
        this.proxyValidator = proxyValidator;
        this.data = data;
    }

    /**
     * Executes the proxy sources client by reading proxy configurations from the {@link ProxyProvider},
     * validating them, and emitting valid proxies as a continuous stream using a {@link Flux}.
     *
     * @param handler The {@link ItemHandler} to handle received proxy configurations.
     */
    @Override
    public void execute(ItemHandler handler) {
        List<ProxyConfigHolder> proxies = provider.readProxy(FILE_NAME_PROXIES);
        validateProxies(proxies);
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
     */
    private void validateProxies(List<ProxyConfigHolder> proxies) {
        if (proxies.isEmpty()) {
            log.error("The proxies list is bad");
        }
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
                .delayElements(Duration.ofSeconds(data.getDelayProxy()))
                .repeat();
    }
}
