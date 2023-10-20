package executor.service.service.impl.proxy;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.service.ProxySourceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        this.proxySourceQueueHandler = new ProxySourceQueueHandler(proxySourceClient, queue, proxyConfigHolder);
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
        List<ProxyConfigHolder> proxies = List.of(proxyConfigHolder);

        when(proxySourceClient.execute()).thenReturn(proxies);
        when(queue.getProxy()).thenReturn(proxyConfigHolder);

        proxySourceQueueHandler.addAllProxies(sizeScenariosList);

        verify(queue, times(sizeScenariosList)).putProxy(proxies.get(0));
        assertSame(proxySourceQueueHandler.getProxy(), proxies.get(0));
    }
}
