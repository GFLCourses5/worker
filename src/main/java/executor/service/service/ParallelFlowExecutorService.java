package executor.service.service;

import executor.service.config.beans.ThreadPoolFactory;

import java.util.concurrent.ExecutorService;

public class ParallelFlowExecutorService {

    private final ExecutionService executionService;
    private final ScenarioSourceListener scenarioListener;
    private final ProxySourcesClient proxyClient;
    private final ExecutorService threadPoolExecutor;


    public ParallelFlowExecutorService(
            ExecutionService executionService,
            ScenarioSourceListener scenarioListener,
            ProxySourcesClient proxyClient) {
        this.executionService = executionService;
        this.scenarioListener = scenarioListener;
        this.proxyClient = proxyClient;
        this.threadPoolExecutor = ThreadPoolFactory.getThreadPoolExecutor();
    }

    public void run() {

    }


}
