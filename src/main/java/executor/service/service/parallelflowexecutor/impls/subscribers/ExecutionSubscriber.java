package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;
import executor.service.queue.ProxyQueue;
import executor.service.queue.ScenarioQueue;
import executor.service.service.executionservice.ExecutionService;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.Task;

import java.util.List;

public class ExecutionSubscriber implements Task {
    private static final long DELAY = 2000L;
    private final ProxyQueue proxyQueue;
    private final ScenarioQueue scenarioQueue;
    private final ExecutionService executionService;
    private final PairGeneratorService pairGeneratorService;
    private final ParallelFlowExecutorService parallelFlow;

    public ExecutionSubscriber(ProxyQueue proxyQueue, ScenarioQueue scenarioQueue,
                               ExecutionService executionService, PairGeneratorService pairGeneratorService,
                               ParallelFlowExecutorService parallelFlow) {
        this.proxyQueue = proxyQueue;
        this.scenarioQueue = scenarioQueue;
        this.executionService = executionService;
        this.pairGeneratorService = pairGeneratorService;
        this.parallelFlow = parallelFlow;
    }

    @Override
    public void run() {
        try {
            while (true) {
                List<ProxyConfigHolder> proxies = proxyQueue.getAllProxy();
                List<Scenario> scenarios = scenarioQueue.getAllScenario();
                List<Pair> pairs = pairGeneratorService.generatePairs(proxies, scenarios);

                pairs.forEach(pair ->
                        parallelFlow.execute(() -> executionService.execute(pair.getScenario(), pair.getProxy())));

                sleep();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

