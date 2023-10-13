package executor.service.service.impl.listener;

import executor.service.model.Scenario;
import executor.service.model.SourceListenerData;
import executor.service.service.ItemHandler;
import executor.service.service.ScenarioProvider;
import executor.service.service.ScenarioSourceListener;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for testing the functionality of the {@code ScenarioSourceListenerImpl} class
 * is an implementation of the {@link ScenarioSourceListener}.
 * This class contains unit tests to verify that {@code ScenarioSourceListenerImpl} is working correctly.
 *
 * @author Kostia Hromovii, Oleksandr Tuleninov.
 * @version 01
 * @see ScenarioProvider
 * @see SourceListenerData
 * @see ScenarioProvider
 * @see ItemHandler
 */
public class ScenarioSourceListenerImplTest {

    @Test
    public void testExecute() {
        ScenarioProvider provider = mock(ScenarioProvider.class);
        ItemHandler<Scenario> handler = mock(ItemHandler.class);
        SourceListenerData sourceListenerData = mock(SourceListenerData.class);
        ScenarioSourceListener listener = new ScenarioSourceListenerImpl(provider, sourceListenerData);

        List<Scenario> scenarios = Arrays.asList(
                new Scenario(),
                new Scenario(),
                new Scenario()
        );

        when(provider.readScenarios(any(String.class))).thenReturn(scenarios);

        listener.execute(handler);

        Flux<Scenario> expectedFlux = Flux.fromIterable(scenarios)
                .delayElements(Duration.ofSeconds(0))
                .repeat();

        StepVerifier.create(expectedFlux)
                .expectNextSequence(scenarios)
                .thenCancel()
                .verify();
    }
}
