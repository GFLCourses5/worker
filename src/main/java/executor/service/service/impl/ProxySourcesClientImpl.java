package executor.service.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.config.properties.PropertiesConstants;
import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxySourcesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ProxySourcesClientImpl implements ProxySourcesClient {
    private final ProxyValidator proxyValidator;
    private static final Logger log = LoggerFactory.getLogger(ProxySourcesClientImpl.class);
    private final ObjectMapper mapper = new ObjectMapper();

    public ProxySourcesClientImpl(ProxyValidator proxyValidator) {
        this.proxyValidator = proxyValidator;
    }

    @Override
    public List<ProxyConfigHolder> getProxies() {
        File file = new File(PropertiesConstants.PROXIES);
        List<ProxyConfigHolder> proxies = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(file)) {
            List<ProxyConfigHolder> input = mapper.readValue(inputStream, new TypeReference<>() {});
            for (ProxyConfigHolder proxy : input) {
                if (proxyValidator.validate(proxy)) {
                    proxies.add(proxy);
                }
            }
            //file.delete();
            return proxies;
        } catch (IOException e) {
            log.info("""
                    Exception with parsing proxy-credentials.json from resources file in the ProxySourcesClientImpl.class.
                    """);
            return null;
        }
    }
}