package executor.service.service.executionservice;

import executor.service.model.scenario.Scenario;
import org.openqa.selenium.WebDriver;

public class ParalleFlowExecutorService {

    private  WebDriver webDriver;
    private ExecutionService executionService;

    public ParalleFlowExecutorService() {
    }

    public ParalleFlowExecutorService(WebDriver webDriver, ExecutionService executionService) {
        this.webDriver = webDriver;
        this.executionService = executionService;
    }

    public void run(Scenario scenario) {

    }
}
