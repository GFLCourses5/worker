package executor.service.service.impl.listener;

import executor.service.model.Scenario;
import executor.service.model.SourceListenerData;
import executor.service.service.ItemHandler;
import executor.service.service.ScenarioProvider;
import executor.service.service.ScenarioSourceListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static executor.service.config.properties.PropertiesConstants.FILE_NAME_SCENARIOS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ScenarioSourceListenerImpl} class
 * is an implementation of the {@link ScenarioSourceListener}.
 * This class contains unit tests to verify that {@code ScenarioSourceListenerImpl} is working correctly.
 *
 * @author Kostia Hromovii, Yurii Kotsiuba.
 * @version 01
 * @see ScenarioSourceListenerImpl
 * @see ScenarioProvider
 * @see SourceListenerData
 * @see ItemHandler
 */
public class ScenarioSourceListenerImplTest {

    private ScenarioSourceListenerImpl listener;
    private ScenarioProvider provider;
    private SourceListenerData sourceListenerData;
    private ItemHandler<Scenario> handler;

    @BeforeEach
    public void setUp() {
        this.provider = mock(ScenarioProvider.class);
        this.handler = mock(ItemHandler.class);
        this.sourceListenerData = mock(SourceListenerData.class);
        this.listener = new ScenarioSourceListenerImpl(provider, sourceListenerData);
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
        when(provider.readScenarios(FILE_NAME_SCENARIOS)).thenReturn(prepareScenarioList());
        when(sourceListenerData.getDelayScenario()).thenReturn(1L);
        doNothing().when(handler).onItemReceived(any(Scenario.class));

        listener.execute(handler);

        verify(handler, times(0)).onItemReceived(any(Scenario.class));
        verify(sourceListenerData).getDelayScenario();
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

