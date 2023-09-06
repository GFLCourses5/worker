package executor.service.service;

import executor.service.config.beans.ThreadPoolFactory;
import executor.service.handler.ProxyHandler;
import executor.service.handler.ScenarioHandler;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public class ParallelFlowExecutorService {

    private static final int CAPACITY = 50;
    private static final Logger log = LoggerFactory.getLogger(ParallelFlowExecutorService.class);
    private ExecutionService executionService;
    private final ScenarioSourceListener scenarioListener;
    private final ProxySourcesClient proxyClient;
    private final ExecutorService threadPoolExecutor;
    private BlockingQueue<Scenario> scenarioQueue;
    private BlockingQueue<ProxyConfigHolder> proxyQueue;


    public ParallelFlowExecutorService(
            ExecutionService executionService,
            ScenarioSourceListener scenarioListener,
            ProxySourcesClient proxyClient) {
        this.executionService = executionService;
        this.scenarioListener = scenarioListener;
        this.proxyClient = proxyClient;
        this.threadPoolExecutor = ThreadPoolFactory.getThreadPoolExecutor();
        this.scenarioQueue = new LinkedBlockingQueue<>(CAPACITY);
        this.proxyQueue = new LinkedBlockingQueue<>(CAPACITY);
    }

    public void run() {
        threadPoolExecutor.submit(() -> scenarioListener.execute(getScenarioHandler()));
        threadPoolExecutor.submit(() -> proxyClient.execute(getProxyHandler()));
        threadPoolExecutor.submit(() -> runExecution());
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
                ProxyConfigHolder proxy = proxyQueue.poll();
                executionService.execute(scenario, proxy);
            }
        } catch (InterruptedException e) {
            log.error("Error while running Thread: {}", e.getMessage(), e);
        }
    }
}