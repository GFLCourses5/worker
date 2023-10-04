package executor.service.service.impl.listener;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.Scenario;
import executor.service.service.ItemHandler;
import executor.service.service.ScenarioProvider;
import executor.service.service.ScenarioSourceListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static executor.service.config.properties.PropertiesConstants.*;
import static executor.service.config.properties.PropertiesConstants.FILE_NAME_SCENARIOS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/**
 * Test class for testing the functionality of the {@code ScenarioSourceListenerImpl} class
 * is an implementation of the {@link ScenarioSourceListener}.
 * This class contains unit tests to verify that {@code ScenarioSourceListenerImpl} is working correctly.
 *
 * @author Kostia Hromovii, Yurii Kotsiuba.
 * @version 01
 * @see executor.service.service.impl.listener.ScenarioSourceListenerImpl
 * @see executor.service.service.ScenarioProvider
 * @see executor.service.config.properties.PropertiesConfig
 * @see executor.service.service.ItemHandler
 */
public class ScenarioSourceListenerImplTest {

    private ScenarioSourceListenerImpl listener;
    private ScenarioProvider provider;
    private Properties properties;
    private PropertiesConfig propertiesConfig;
    private ItemHandler<Scenario> handler;

    @BeforeEach
    public void setUp() {
        this.provider = mock(ScenarioProvider.class);
        this.handler = mock(ItemHandler.class);
        this.properties = mock(Properties.class);
        this.propertiesConfig = mock(PropertiesConfig.class);
        this.listener = new ScenarioSourceListenerImpl(provider, propertiesConfig);
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
        when(provider.readScenarios(FILE_NAME_SCENARIOS)).thenReturn(prepareScenarioList());
        when(propertiesConfig.getProperties(SOURCES_PROPERTIES)).thenReturn(properties);
        when(properties.getProperty(DELAY_SCENARIO_SECONDS)).thenReturn("1");
        doNothing().when(handler).onItemReceived(any(Scenario.class));

        listener.execute(handler);

        verify(handler, times(0)).onItemReceived(any(Scenario.class));
        verify(propertiesConfig).getProperties(SOURCES_PROPERTIES);
        verify(properties).getProperty(DELAY_SCENARIO_SECONDS);
        verify(provider).readScenarios(FILE_NAME_SCENARIOS);
    }

    @Test
    public void testExecute_throwException_whenNullList() {
        given(provider.readScenarios(FILE_NAME_SCENARIOS)).willReturn(null);

        assertThrows(NullPointerException.class, ()-> listener.execute(handler));
        verify(provider).readScenarios(FILE_NAME_SCENARIOS);
    }

    private List<Scenario> prepareScenarioList() {
        return Arrays.asList(
                new Scenario(),
                new Scenario(),
                new Scenario()
        );
    }
}

