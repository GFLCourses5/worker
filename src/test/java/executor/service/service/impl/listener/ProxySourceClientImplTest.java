package executor.service.service.impl.listener;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.SourceListenerData;
import executor.service.service.ItemHandler;
import executor.service.service.ProxyProvider;
import executor.service.service.ProxySourceClient;
import executor.service.service.ProxyValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static executor.service.config.properties.PropertiesConstants.FILE_NAME_PROXIES;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ProxySourcesClientImpl} class
 * is an implementation of the {@link ProxySourceClient}.
 * This class contains unit tests to verify that {@code ProxySourcesClientImpl} is working correctly.
 *
 * @author Dima Silenko, Yurii Kotsiuba.
 * @version 01
 * @see ProxySourceClientImpl
 * @see ProxyProvider
 * @see SourceListenerData
 * @see ItemHandler
 */
public class ProxySourceClientImplTest {

    private ProxySourceClientImpl listener;
    private ProxyProvider provider;
    private ProxyValidator proxyValidator;
    private SourceListenerData sourceListenerData;
    private ItemHandler<ProxyConfigHolder> handler;

    @BeforeEach
    public void setUp() {
        this.provider = mock(ProxyProvider.class);
        this.handler = mock(ItemHandler.class);
        this.proxyValidator = mock(ProxyValidator.class);
        this.sourceListenerData = mock(SourceListenerData.class);
        this.listener = new ProxySourceClientImpl(provider, proxyValidator, sourceListenerData);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(sourceListenerData);
        verifyNoMoreInteractions(handler);
        verifyNoMoreInteractions(provider);
        this.listener = null;
    }

    @Test
    public void testExecute_numberOfTimes() {
        when(provider.readProxy(FILE_NAME_PROXIES)).thenReturn(prepareProxyList());
        when(sourceListenerData.getDelayProxy()).thenReturn(1L);
        when(proxyValidator.isValid(any(ProxyConfigHolder.class))).thenReturn(true);
        doNothing().when(handler).onItemReceived(any(ProxyConfigHolder.class));

        listener.execute(handler);

        verify(handler, times(0)).onItemReceived(any(ProxyConfigHolder.class));
        verify(sourceListenerData).getDelayProxy();
        verify(provider).readProxy(FILE_NAME_PROXIES);
    }

    @Test
    public void testExecute_throwException_whenNullList() {
        given(provider.readProxy(FILE_NAME_PROXIES)).willReturn(null);

        assertThrows(NullPointerException.class, () -> listener.execute(handler));
        verify(provider).readProxy(FILE_NAME_PROXIES);
    }

    private List<ProxyConfigHolder> prepareProxyList() {
        return Arrays.asList(
                new ProxyConfigHolder(),
                new ProxyConfigHolder(),
                new ProxyConfigHolder()
        );
    }
}
