package executor.service.service.impl.listener;

import executor.service.config.properties.PropertiesConfig;
import executor.service.config.properties.PropertiesConstants;
import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxyValidator;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * The {@code ProxyValidatorImpl} class is responsible for checking the proxy functionality
 * by sending http request to specified url and awaiting a http response code 200
 *
 * @author Oleksii Bondarenko
 * @version 0.2
 */
@Service
public class ProxyValidatorImpl implements ProxyValidator {

    private static final Logger log = LoggerFactory.getLogger(ProxyValidatorImpl.class);

    private final Properties properties;

    public ProxyValidatorImpl(PropertiesConfig propertiesConfig) {
        this.properties = propertiesConfig.getProperties(PropertiesConstants.PROXY_VALIDATOR_PROPERTIES);
    }

    public Boolean isValid(ProxyConfigHolder proxyConfigHolder) {
        int responseCode;

        try {
            CredentialsProvider credentialsProvider = getCredentialsProvider(proxyConfigHolder);
            CloseableHttpClient httpClient = getHttpClient(proxyConfigHolder, credentialsProvider);


            HttpGet httpGet = new HttpGet(properties.getProperty(PropertiesConstants.PROXY_VALIDATOR_TARGET_URL));
            CloseableHttpResponse response = httpClient.execute(httpGet);
            responseCode = response.getStatusLine().getStatusCode();

            response.close();
            httpClient.close();
        } catch (Exception e) {
            log.error("Proxy [" + proxyConfigHolder.getProxyNetworkConfig().getHostname() + ":" +
                    proxyConfigHolder.getProxyNetworkConfig().getPort() + "] is not valid");
            return false;
        }
        return responseCode == HttpStatus.SC_OK;
    }

    private CredentialsProvider getCredentialsProvider(ProxyConfigHolder proxyConfig) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(proxyConfig.getProxyNetworkConfig().getHostname(),
                        proxyConfig.getProxyNetworkConfig().getPort()),
                new UsernamePasswordCredentials(proxyConfig.getProxyCredentials().getUsername(),
                        proxyConfig.getProxyCredentials().getPassword()));
        return credentialsProvider;
    }

    private CloseableHttpClient getHttpClient(ProxyConfigHolder proxyConfig, CredentialsProvider credentials) {
        int timeout = Integer.parseInt(properties.getProperty(PropertiesConstants.PROXY_VALIDATOR_CONNECTION_TIMEOUT));
        return HttpClients.custom()
                .setDefaultCredentialsProvider(credentials)
                .setProxy(new HttpHost(proxyConfig.getProxyNetworkConfig().getHostname(),
                        proxyConfig.getProxyNetworkConfig().getPort()))
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(timeout)
                        .setSocketTimeout(timeout)
                        .build())
                .build();
    }
}
