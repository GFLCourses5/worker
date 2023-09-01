package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import reactor.core.publisher.Flux;

public interface ProxySourcesClient {

    Flux<ProxyConfigHolder> getProxies();

}
