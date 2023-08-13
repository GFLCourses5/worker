package executor.service.service.stepexecutors.selenium;

import executor.service.model.entity.Step;
import org.openqa.selenium.WebDriver;

public class StepExecutionClickCssImpl implements StepExecutionClickCss {
    @Override
    public String getStepAction() {
        return "do smth";
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        step.setValue("1");
    }
}
