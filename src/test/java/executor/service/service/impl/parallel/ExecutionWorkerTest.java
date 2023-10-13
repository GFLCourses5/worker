package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ExecutionWorker} class
 * is an implementation of the {@link Runnable}.
 * This class contains unit tests to verify that {@code ExecutionWorker} is working correctly.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see executor.service.service.ExecutionService
 * @see executor.service.model.Scenario
 * @see executor.service.model.ProxyConfigHolder
 */
public class ExecutionWorkerTest {

    @Test
    public void testRunPositive() {
        var scenario = new Scenario();
        var proxy = new ProxyConfigHolder();

        var service = mock(ExecutionService.class);
        doNothing().when(service).execute(scenario, proxy);

        var executionWorker =  new ExecutionWorker(service, scenario, proxy);
        executionWorker.run();

        verify(service, times(1)).execute(any(Scenario.class), any(ProxyConfigHolder.class));
    }
}
