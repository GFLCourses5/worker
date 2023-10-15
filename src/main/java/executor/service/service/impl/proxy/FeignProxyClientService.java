package executor.service.service.impl.proxy;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.request.Mode;
import executor.service.model.request.ProxyParameters;
import executor.service.model.response.WebshareResponse;
import executor.service.service.FeignProxyClient;
import executor.service.service.ProxyClientService;
import executor.service.service.impl.proxy.mapper.ProxyResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that implements the ProxyClientService interface using Feign.
 * <p>
 * This class provides an implementation of the ProxyClientService interface
 * by making HTTP requests to the Webshare Proxy API using the Feign client.
 *
 * @see ProxyClientService
 * @see FeignProxyClient
 * <p>
 *
 * @author Yurii Kotsiuba
 * @version 01
 */
@Service
public class FeignProxyClientService implements ProxyClientService {

    private final FeignProxyClient client;

    public FeignProxyClientService(FeignProxyClient client) {
        this.client = client;
    }

    /**
     * Retrieves a list of proxy configurations from the Webshare Proxy API.
     *
     * @return A list of {@link ProxyConfigHolder} objects representing the proxy configurations.
     */
    @Override
    public List<ProxyConfigHolder> getProxyList() {
        WebshareResponse response = client.getProxyList(getProxyParameters());
        return ProxyResponseMapper.toCollectionDto(response.getResults());
    }

    private ProxyParameters getProxyParameters() {
        return new ProxyParameters(Mode.DIRECT.getName(), true);
    }
}
