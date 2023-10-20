package executor.service.service.impl.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.ParallelFlowExecutorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class DeadlockParallelFlowExecutorServiceImplTest {

    @Autowired
    ParallelFlowExecutorService service;

    @MockBean
    ScenarioSourceQueue scenarioSourceQueue;

    @MockBean
    ExecutionService executionService;

    @MockBean
    ProxySourceQueue proxySourceQueue;


    @Test
    public  void whenCatchRuntimeError() {

        when(scenarioSourceQueue.getScenario()).thenReturn(new Scenario()).thenThrow(new RuntimeException()).thenReturn(new Scenario());
        when(proxySourceQueue.getProxy()).thenReturn(new ProxyConfigHolder()).thenReturn(new ProxyConfigHolder()).thenReturn(new ProxyConfigHolder());

        //verify(executionService, times(3)).execute();
        verify(service, times(3)).execute();



    }


}