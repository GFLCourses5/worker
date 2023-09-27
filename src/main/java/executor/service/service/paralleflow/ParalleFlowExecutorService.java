package executor.service.service.paralleflow;

import executor.service.model.scenario.Scenario;
import executor.service.service.executionservice.ExecutionService;
import org.openqa.selenium.WebDriver;

public class ParalleFlowExecutorService {

    private final WebDriver webDriver;
    private final ExecutionService executionService;

    public ParalleFlowExecutorService(WebDriver webDriver, ExecutionService executionService) {
        this.webDriver = webDriver;
        this.executionService = executionService;
    }

    public void run(Scenario scenario) {

    }
}
