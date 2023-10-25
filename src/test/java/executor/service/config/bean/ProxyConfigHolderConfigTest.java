package executor.service.config.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class contains unit tests for the {@link ProxyConfigHolderConfig} class.
 * <p>
 *
 * @author Dima Silenko, Oleksandr Tuleninov
 * @version 01
 */
public class ProxyConfigHolderConfigTest {

    private ProxyConfigHolderConfig config;

    @BeforeEach
    public void setUp() {
        this.config = new ProxyConfigHolderConfig();
    }

    @Test
    public void testProxyConfigHolder() {
        var proxy = config.proxyConfigHolder();

        assertNotNull(proxy, "ProxyConfigHolder should not be null");
        assertNotNull(proxy.getProxyNetworkConfig(), "ProxyNetworkConfig should not be null");
        assertNotNull(proxy.getProxyCredentials(), "ProxyCredentials should not be null");
    }
}
