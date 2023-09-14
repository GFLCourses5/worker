package executor.service.service.impl;

import executor.service.handler.ProxyHandler;
import executor.service.handler.ScenarioHandler;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ScenarioSourceListener;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {
    private static final int CAPACITY = 50;
    private final ExecutionService executionService;
    private final ScenarioSourceListener scenarioListener;
    private final ProxySourcesClient proxyClient;
    private final ExecutorService threadPoolExecutor;
    private final BlockingQueue<Scenario> scenarioQueue;
    private final BlockingQueue<ProxyConfigHolder> proxyQueue;

    public ParallelFlowExecutorServiceImpl(
            ExecutionService executionService,
            ScenarioSourceListener scenarioListener,
            ProxySourcesClient proxyClient,
            ExecutorService threadPoolExecutor) {
        this.executionService = executionService;
        this.scenarioListener = scenarioListener;
        this.proxyClient = proxyClient;
        this.threadPoolExecutor = threadPoolExecutor;
        this.scenarioQueue = new LinkedBlockingQueue<>(CAPACITY);
        this.proxyQueue = new LinkedBlockingQueue<>(CAPACITY);
    }

    @Override
    public void run() {
        threadPoolExecutor.execute(() -> scenarioListener.execute(getScenarioHandler()));
        threadPoolExecutor.execute(() -> proxyClient.execute(getProxyHandler()));
        threadPoolExecutor.execute(this::runExecution);
    }

    private ScenarioHandler getScenarioHandler() {
        return scenario -> {
            try {
                scenarioQueue.put(scenario);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    private ProxyHandler getProxyHandler() {
        return proxy -> {
            try {
                proxyQueue.put(proxy);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    private void runExecution() {
        try {
            while (true) {
                Scenario scenario = scenarioQueue.take();
                ProxyConfigHolder proxy = proxyQueue.take();
                threadPoolExecutor.execute(() -> executionService.execute(scenario, proxy));
            }
        } catch (InterruptedException e) {
            System.err.printf("Error while running Thread: %s", e.getMessage());
        }
    }
}
