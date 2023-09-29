package executor.service.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONFileProxyProvider implements ProxyProvider {

    private static final Logger log = LoggerFactory.getLogger(ProxyProvider.class);

    @Override
    public List<ProxyConfigHolder> readProxy(String fileName) {
        List<ProxyConfigHolder> proxies = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            proxies = parseScenariosFromJson(reader);
        } catch (IOException e) {
            log.error("Cannot read file {}. Reason: {}", fileName, e.getMessage(), e);
        }
        return proxies;
    }

    private List<ProxyConfigHolder> parseScenariosFromJson(BufferedReader reader) {
        Type listType = new TypeToken<List<ProxyConfigHolder>>() {}.getType();
        return new Gson().fromJson(reader, listType);
    }
}
