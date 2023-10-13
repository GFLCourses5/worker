package executor.service.config;

import executor.service.service.ParallelFlowExecutorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class WorkerApplicationRunner implements ApplicationRunner {

    private final ParallelFlowExecutorService parallelFlowExecutor;

    public WorkerApplicationRunner(ParallelFlowExecutorService parallelFlowExecutor) {
        this.parallelFlowExecutor = parallelFlowExecutor;
    }

    @Override
    public void run(ApplicationArguments args) {
        parallelFlowExecutor.execute();
    }
}
