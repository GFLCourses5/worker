package executor.service.service.impl.proxy;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProxyValidatorImplTest {

    @Mock
    private ProxyValidatorImpl proxyValidator;

    @BeforeEach
    public void setUp() {
        proxyValidator = new ProxyValidatorImpl();
        try {
            Field targetUrl = proxyValidator.getClass().getDeclaredField("targetUrl");
            targetUrl.setAccessible(true);
            targetUrl.set(proxyValidator, "https://google.com");

            Field connectionTimeout = proxyValidator.getClass().getDeclaredField("connectionTimeout");
            connectionTimeout.setAccessible(true);
            connectionTimeout.set(proxyValidator, "10000");

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testValidProxy() {
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();
        proxyConfigHolder.setProxyNetworkConfig(new ProxyNetworkConfig("38.154.227.167", 5868));
        proxyConfigHolder.setProxyCredentials(new ProxyCredentials("ztzzilba", "l6q37njhw5s2"));

        assertTrue(proxyValidator.isValid(proxyConfigHolder));
    }

    @Test
    public void testInvalidCredentials() {
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();
        proxyConfigHolder.setProxyNetworkConfig(new ProxyNetworkConfig("38.154.227.167", 5868));
        proxyConfigHolder.setProxyCredentials(new ProxyCredentials("login", "password"));

        assertFalse(proxyValidator.isValid(proxyConfigHolder));
    }

    @Test
    public void testInvalidProxy() {
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();
        proxyConfigHolder.setProxyNetworkConfig(new ProxyNetworkConfig("invalid_host", 8888));
        proxyConfigHolder.setProxyCredentials(new ProxyCredentials("username", "password"));

        assertFalse(proxyValidator.isValid(proxyConfigHolder));
    }
}
