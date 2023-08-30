package executor.service.service.executors;

import executor.service.model.entity.Scenario;
import org.openqa.selenium.WebDriver;

public class ParallelFlowExecutorService {

    private  WebDriver webDriver;
    private  ExecutionService executionService;

    public ParalleFlowExecutorService() {
    }

    public ParallelFlowExecutorService(WebDriver webDriver, ExecutionService executionService) {
        this.webDriver = webDriver;
        this.executionService = executionService;
    }

    public void run(Scenario scenario) {

    }
}
