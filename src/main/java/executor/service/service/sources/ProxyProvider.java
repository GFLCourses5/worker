package executor.service.service.sources;

import executor.service.model.proxy.ProxyConfigHolder;
import java.util.List;

public interface ProxyProvider {

  List<ProxyConfigHolder> readProxy();
}
