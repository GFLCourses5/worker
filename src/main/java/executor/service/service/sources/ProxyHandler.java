package executor.service.service.sources;


import executor.service.model.proxy.ProxyConfigHolder;

public interface ProxyHandler {
    void onProxyReceived(ProxyConfigHolder proxy);
}
