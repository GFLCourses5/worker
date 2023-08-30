package executor.service.service.impl;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.service.ProxySourcesClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProxySourcesClientImpl implements ProxySourcesClient {

    private static final String PROXY_CREDENTIALS_FILE = "/proxy-credentials.json";
    private static final String PROXY_NETWORK_CONFIG_FILE = "/proxy-network.json";

    @Override
    public ProxyConfigHolder getProxy() {
        ProxyCredentials proxyCredentials = readProxyCredentialsFromJson();
        ProxyNetworkConfig proxyNetworkConfig = readProxyNetworkConfigFromJson();
        return new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);
    }

    private ProxyCredentials readProxyCredentialsFromJson() {
        try (InputStream inputStream = getClass().getResourceAsStream(PROXY_CREDENTIALS_FILE)) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<ProxyCredentials> credentialsList = objectMapper.readValue(inputStream, new TypeReference<List<ProxyCredentials>>() {});
            return credentialsList.get(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ProxyNetworkConfig readProxyNetworkConfigFromJson() {
        try (InputStream inputStream = getClass().getResourceAsStream(PROXY_NETWORK_CONFIG_FILE)) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<ProxyNetworkConfig> networkConfigList = objectMapper.readValue(inputStream, new TypeReference<List<ProxyNetworkConfig>>() {});
            return networkConfigList.get(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}