package executor.service.di;

import executor.service.App;
import executor.service.config.di.ApplicationContext;
import executor.service.config.di.FactoryForDI;
import executor.service.service.*;
import executor.service.service.parallel.ParallelFlowExecutorServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DITest {

    private ApplicationContext context;

    {
        context = FactoryForDI.run(
                getPackageName(),
                new HashMap<>(Map.of(ParallelFlowExecutorService.class, ParallelFlowExecutorServiceImpl.class)));
    }

    @Test
    public void testParallelFlowExecutorService() {
        var service1 = context.getObject(ParallelFlowExecutorService.class);
        assertNotNull(service1);

        // checking for singleton
        var service2 = context.getObject(ParallelFlowExecutorService.class);
        assertNotNull(service2);
        assertEquals(service1, service2);

        //check that singleton using cache
        var objectFromCache = context.getObject(ParallelFlowExecutorService.class);
        assertEquals(service1, objectFromCache);
    }

    @Test
    public void testExecutionService() {
        var service1 = context.getObject(ExecutionService.class);
        assertNotNull(service1);

        // checking for singleton
        var service2 = context.getObject(ExecutionService.class);
        assertNotNull(service2);
        assertEquals(service1, service2);

        //check that singleton using cache
        var objectFromCache = context.getObject(ExecutionService.class);
        assertEquals(service1, objectFromCache);
    }

    @Test
    public void testScenarioExecutor() {
        var executor1 = context.getObject(ScenarioExecutor.class);
        assertNotNull(executor1);

        // checking for singleton
        var executor2 = context.getObject(ScenarioExecutor.class);
        assertNotNull(executor2);
        assertEquals(executor1, executor2);

        //check that singleton using cache
        var objectFromCache = context.getObject(ScenarioExecutor.class);
        assertEquals(executor1, objectFromCache);
    }

    @Test
    public void testStepExecutionClickCss() {
        var clickCss1 = context.getObject(StepExecutionClickCss.class);
        assertNotNull(clickCss1);

        // checking for singleton
        var clickCss2 = context.getObject(StepExecutionClickCss.class);
        assertNotNull(clickCss2);
        assertEquals(clickCss1, clickCss2);

        //check that singleton using cache
        var objectFromCache = context.getObject(StepExecutionClickCss.class);
        assertEquals(clickCss1, objectFromCache);
    }

    @Test
    public void testStepExecutionSleep() {
        var sleep1 = context.getObject(StepExecutionSleep.class);
        assertNotNull(sleep1);

        // checking for singleton
        var sleep2 = context.getObject(StepExecutionSleep.class);
        assertNotNull(sleep2);
        assertEquals(sleep1, sleep2);

        //check that singleton using cache
        var objectFromCache = context.getObject(StepExecutionSleep.class);
        assertEquals(sleep1, objectFromCache);
    }

    @Test
    public void testStepExecutionClickXpath() {
        var clickXpath1 = context.getObject(StepExecutionClickXpath.class);
        assertNotNull(clickXpath1);

        // checking for singleton
        var clickXpath2 = context.getObject(StepExecutionClickXpath.class);
        assertNotNull(clickXpath2);
        assertEquals(clickXpath1, clickXpath2);

        //check that singleton using cache
        var objectFromCache = context.getObject(StepExecutionClickXpath.class);
        assertEquals(clickXpath1, objectFromCache);
    }

    private static String getPackageName() {
        Package currentPackage = App.class.getPackage();
        return currentPackage.getName();
    }
}
