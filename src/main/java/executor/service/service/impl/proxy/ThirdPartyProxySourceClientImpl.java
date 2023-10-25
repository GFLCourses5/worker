package executor.service.service.impl.proxy;

import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxyClientService;
import executor.service.service.ProxySourceClient;
import executor.service.service.ProxyValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ThirdPartyProxySourceClientImpl} class implements the {@link ProxySourceClient} interface
 * to retrieve a list of proxy configurations from a third-party proxy client service.
 * It uses a {@link ProxyClientService} to fetch the proxy configurations.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ProxySourceClient
 * @see ProxyClientService
 * @see ProxyConfigHolder
 */
@Service
public class ThirdPartyProxySourceClientImpl implements ProxySourceClient {

    private final ProxyClientService service;
    private final ProxyValidator validator;

    public ThirdPartyProxySourceClientImpl(ProxyClientService service,
                                           ProxyValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    /**
     * Retrieve a list of proxy configurations from the third-party proxy client service.
     *
     * @return A list of {@link ProxyConfigHolder} objects representing proxy configurations.
     */
    @Override
    public List<ProxyConfigHolder> execute() {
        List<ProxyConfigHolder> proxyList = service.getProxyList();
        List<ProxyConfigHolder> validatedProxies = new ArrayList<>();
        validation(proxyList, validatedProxies);

        return validatedProxies;
    }

    private void validation(List<ProxyConfigHolder> proxyList,
                            List<ProxyConfigHolder> validatedProxies) {
        for (ProxyConfigHolder proxy : proxyList) {
            if (validator.isValid(proxy)) {
                validatedProxies.add(proxy);
            }
        }
    }
}
