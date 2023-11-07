package executor.service.service.impl.proxy;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The {@code ProxyValidatorImpl} class is responsible for checking the proxy functionality
 * by sending http request to specified url and awaiting a http response code 200
 *
 * @author Oleksii Bondarenko
 * @version 0.3
 */
@Service
public class ProxyValidatorImpl implements ProxyValidator {

    private static final Logger log = LoggerFactory.getLogger(ProxyValidatorImpl.class);

    @Value("${validator.targetUrl}")
    private String targetUrl;
    @Value("${validator.connectionTimeout}")
    private String connectionTimeout;

    public Boolean isValid(ProxyConfigHolder proxyConfigHolder) {
        try (CloseableHttpClient httpClient = getHttpClient(proxyConfigHolder)){
            CloseableHttpResponse response = httpClient.execute(new HttpGet(targetUrl));

            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (IOException e) {
            log.error("Proxy [" + proxyConfigHolder.getProxyNetworkConfig().getHostname() + ":" +
                    proxyConfigHolder.getProxyNetworkConfig().getPort() + "] is not valid");

            return false;
        }
    }

    private CloseableHttpClient getHttpClient(ProxyConfigHolder proxyConfig) {
        int timeout = Integer.parseInt(connectionTimeout);
        return HttpClients.custom()
                .setDefaultCredentialsProvider(getCredentialsProvider(proxyConfig))
                .setProxy(new HttpHost(proxyConfig.getProxyNetworkConfig().getHostname(),
                        proxyConfig.getProxyNetworkConfig().getPort()))
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(timeout)
                        .setSocketTimeout(timeout)
                        .build())
                .build();
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
}
