package executor.service.service;

import executor.service.model.ProxyConfigHolder;

public interface ProxyHandler {

    void onItemReceived(ProxyConfigHolder item);

}
