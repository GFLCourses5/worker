package executor.service.service.impl.proxy;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.service.ProxySourceClient;
import executor.service.service.ProxyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ProxySourceQueueHandler} class.
 * This class contains unit tests to verify that {@code ProxySourceQueueHandler} is working correctly.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ProxySourceQueueHandler
 * @see ProxySourceClient
 * @see ProxySourceQueue
 * @see ProxyConfigHolder
 */
public class ProxySourceQueueHandlerTest {

    private ProxySourceQueueHandler proxySourceQueueHandler;
    private ProxySourceClient proxySourceClient;
    private ProxySourceQueue queue;
    private ProxyConfigHolder proxyConfigHolder;

    @BeforeEach
    public void setUp() {
        this.proxySourceClient = mock(ProxySourceClient.class);
        this.queue = mock(ProxySourceQueue.class);
        this.proxyConfigHolder = new ProxyConfigHolder(new ProxyNetworkConfig(), new ProxyCredentials());
        ProxyValidator validator = mock(ProxyValidator.class);
        this.proxySourceQueueHandler = new ProxySourceQueueHandler(
                proxySourceClient, queue, proxyConfigHolder, validator);
    }

    @Test
    public void testSetNoProxy() {
        int sizeScenariosList = 5;
        proxySourceQueueHandler.setNoProxy(sizeScenariosList);

        verify(queue, times(sizeScenariosList)).putProxy(proxyConfigHolder);
    }

    @Test
    public void testGetProxy() {
        when(queue.getProxy()).thenReturn(proxyConfigHolder);

        var result = proxySourceQueueHandler.getProxy();

        assertNotNull(result);
        assertEquals(proxyConfigHolder, result);
    }

    @Test
    public void testAddAllProxies() {
        int sizeScenariosList = 5;
        List<ProxyConfigHolder> proxiesToAdd = new ArrayList<>();
        for (int i = 0; i < sizeScenariosList; i++) {
            proxiesToAdd.add(proxyConfigHolder);
        }

        List<ProxyConfigHolder> proxiesCache = new ArrayList<>(proxiesToAdd);

        when(proxySourceClient.execute()).thenReturn(proxiesToAdd);

        proxySourceQueueHandler.addAllProxies(sizeScenariosList);

        for (int i = 0; i < sizeScenariosList; i++) {
            verify(queue, times(sizeScenariosList)).putProxy(proxiesCache.get(i));
        }
        verify(proxySourceClient, times(1)).execute();
    }
}
