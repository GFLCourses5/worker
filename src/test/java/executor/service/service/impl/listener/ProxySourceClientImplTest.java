package executor.service.service.impl.listener;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ProxyConfigHolder;
import executor.service.service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static executor.service.config.properties.PropertiesConstants.*;
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
 * @see PropertiesConfig
 * @see ItemHandler
 */
public class ProxySourceClientImplTest {

    private ProxySourceClientImpl listener;
    private ProxyProvider provider;
    private ProxyValidator proxyValidator;
    private Properties properties;
    private PropertiesConfig propertiesConfig;
    private ItemHandler<ProxyConfigHolder> handler;

    @BeforeEach
    public void setUp() {
        this.provider = mock(ProxyProvider.class);
        this.handler = mock(ItemHandler.class);
        this.proxyValidator = mock(ProxyValidator.class);
        this.properties = mock(Properties.class);
        this.propertiesConfig = mock(PropertiesConfig.class);
        this.listener = new ProxySourceClientImpl(provider, proxyValidator, propertiesConfig);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(propertiesConfig);
        verifyNoMoreInteractions(properties);
        verifyNoMoreInteractions(handler);
        verifyNoMoreInteractions(provider);
        this.listener = null;
    }

    @Test
    public void testExecute_numberOfTimes() {
        when(provider.readProxy(FILE_NAME_PROXIES)).thenReturn(prepareProxyList());
        when(propertiesConfig.getProperties(SOURCES_PROPERTIES)).thenReturn(properties);
        when(properties.getProperty(DELAY_PROXY_SECONDS)).thenReturn("1");
        when(proxyValidator.isValid(any(ProxyConfigHolder.class))).thenReturn(true);
        doNothing().when(handler).onItemReceived(any(ProxyConfigHolder.class));

        listener.execute(handler);

        verify(handler, times(0)).onItemReceived(any(ProxyConfigHolder.class));
        verify(propertiesConfig).getProperties(SOURCES_PROPERTIES);
        verify(properties).getProperty(DELAY_PROXY_SECONDS);
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
