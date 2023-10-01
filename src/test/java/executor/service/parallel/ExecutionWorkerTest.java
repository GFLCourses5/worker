package executor.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.parallel.ExecutionWorker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ExecutionWorker} class
 * is an implementation of the {@link Runnable}.
 * This class contains unit tests to verify that {@code ExecutionWorker} is working correctly.
 *
 *  @author Oleksandr Tuleninov
 *  @version 01
 * */
public class ExecutionWorkerTest {

    private ExecutionService service;
    private Scenario scenario;
    private ProxyConfigHolder proxy;
    private ExecutionWorker executionWorker;

    @BeforeEach
    public void setUp() {
        this.service = mock(ExecutionService.class);
        this.scenario = mock(Scenario.class);
        this.proxy = mock(ProxyConfigHolder.class);
        this.executionWorker = new ExecutionWorker(service, scenario, proxy);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(service);
        this.service = null;
        this.scenario = null;
        this.proxy = null;
        this.executionWorker = null;
    }

    @Test
    public void testRunPositive() {
        doNothing().when(service).execute(scenario, proxy);

        executionWorker.run();

        verify(service, times(1)).execute(scenario, proxy);
    }
}
