package executor.service.service.sources.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.service.sources.ProxyProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONFileProxyProvider implements ProxyProvider {

  private static final Logger log = LoggerFactory.getLogger(ProxyProvider.class);
  private static final String FILE_NAME = "/proxy.json";

  @Override
  public List<ProxyConfigHolder> readProxy() {
    List<ProxyConfigHolder> proxies = null;
    try (InputStream inputStream = getClass().getResourceAsStream(FILE_NAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      proxies = parseScenariosFromJson(reader);
    } catch (IOException e) {
      log.error("Cannot read file {}. Reason: {}", FILE_NAME, e.getMessage(), e);
    }
    return proxies;
  }

  private List<ProxyConfigHolder> parseScenariosFromJson(BufferedReader reader) {
    Type listType = new TypeToken<List<ProxyConfigHolder>>() {
    }.getType();
    return new Gson().fromJson(reader, listType);
  }
}
