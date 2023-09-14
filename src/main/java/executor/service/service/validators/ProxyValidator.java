package executor.service.service.validators;


import executor.service.model.proxy.ProxyConfigHolder;

public interface ProxyValidator {

  Boolean isValid(ProxyConfigHolder proxyConfigHolder);
}
