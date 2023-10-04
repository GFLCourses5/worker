package executor.service.service.impl.listener;

import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxyProvider;
import org.junit.jupiter.api.Test;

import java.util.List;

import static executor.service.config.properties.PropertiesConstants.FILE_NAME_PROXIES;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for testing the functionality of the {@code JSONFileProxyProvider} class
 * is an implementation of the {@link ProxyProvider}.
 * This class contains unit tests to verify that {@code JSONFileProxyProvider} is working correctly.
 * <p>
 *
 * @author Dima Silenko, Yurii Kotsiuba.
 * @version 01
 * @see ProxyProvider
 * @see JSONFileProxyProvider
 * @see ProxyConfigHolder
 */
public class JSONFileProxyProviderTest {

    @Test
    void testReadProxies_type() {
        ProxyProvider provider = new JSONFileProxyProvider();
        List<ProxyConfigHolder> proxies = provider.readProxy(FILE_NAME_PROXIES);

        for (ProxyConfigHolder proxy : proxies) {
            assertEquals(ProxyConfigHolder.class, proxy.getClass());
        }
    }
}
