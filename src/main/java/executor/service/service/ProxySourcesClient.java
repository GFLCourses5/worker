package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ProxySourcesClient {

    //void execute(ProxyHandler handler);
    Flux<ProxyConfigHolder> execute();

}
