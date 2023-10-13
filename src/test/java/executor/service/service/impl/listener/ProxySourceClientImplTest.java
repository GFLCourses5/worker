package executor.service.service.impl.listener;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.SourceListenerData;
import executor.service.service.ItemHandler;
import executor.service.service.ProxyProvider;
import executor.service.service.ProxySourceClient;
import executor.service.service.ProxyValidator;
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
 * Test class for testing the functionality of the {@code ProxySourcesClientImpl} class
 * is an implementation of the {@link ProxySourceClient}.
 * This class contains unit tests to verify that {@code ProxySourcesClientImpl} is working correctly.
 *
 * @author Dima Silenko, Oleksandr Tuleninov.
 * @version 01
 * @see ProxyProvider
 * @see ProxyValidator
 * @see SourceListenerData
 * @see ItemHandler
 */
public class ProxySourceClientImplTest {

    @Test
    public void testExecute() {
        ProxyProvider provider = mock(ProxyProvider.class);
        ItemHandler<ProxyConfigHolder> handler = mock(ItemHandler.class);
        SourceListenerData sourceListenerData = mock(SourceListenerData.class);
        ProxyValidator validator = mock(ProxyValidator.class);
        ProxySourceClient listener = new ProxySourceClientImpl(provider, validator, sourceListenerData);

        List<ProxyConfigHolder> proxies = Arrays.asList(
                new ProxyConfigHolder(),
                new ProxyConfigHolder(),
                new ProxyConfigHolder()
        );

        when(provider.readProxy(any(String.class))).thenReturn(proxies);

        listener.execute(handler);

        Flux<ProxyConfigHolder> expectedFlux = Flux.fromIterable(proxies)
                .delayElements(Duration.ofSeconds(0))
                .repeat();

        StepVerifier.create(expectedFlux)
                .expectNextSequence(proxies)
                .thenCancel()
                .verify();
    }
}
