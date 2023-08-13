package executor.service.service.executors;

import executor.service.model.entity.Scenario;
import org.openqa.selenium.WebDriver;

public class ParallelFlowExecutorService {

    private final WebDriver webDriver;
    private final ExecutionService executionService;

    public ParallelFlowExecutorService(WebDriver webDriver, ExecutionService executionService) {
        this.webDriver = webDriver;
        this.executionService = executionService;
    }

    public void run(Scenario scenario) {

    }
}
