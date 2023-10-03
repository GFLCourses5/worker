package executor.service.service.stepexecution.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.scenario.StepAction;
import executor.service.service.stepexecution.StepExecution;
import executor.service.service.stepexecution.StepExecutionFabric;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class StepExecutionFabricImplTest {

    @Test
    public void emptyFactory() {
        StepExecutionFabric fabric = new StepExecutionFabricImpl(List.of());

        assertThrows(StepExecutionException.class,() ->fabric.getStepExecutor(StepAction.SLEEP.getName()));
        assertThrows(StepExecutionException.class,() ->fabric.getStepExecutor(StepAction.CLICK_CSS.getName()));
        assertThrows(StepExecutionException.class,() ->fabric.getStepExecutor(StepAction.CLICK_XPATH.getName()));

        assertThrows(StepExecutionException.class,() ->fabric.getStepExecutor(StepAction.SLEEP));
        assertThrows(StepExecutionException.class,() ->fabric.getStepExecutor(StepAction.CLICK_CSS));
        assertThrows(StepExecutionException.class,() ->fabric.getStepExecutor(StepAction.CLICK_XPATH));

    }


    @Test
    public void fullFactory() {

        StepExecution sleep = new StepExecutionSleepImpl();
        StepExecution clickXpath = new StepExecutionClickXpathImpl();
        StepExecution clickCss = new StepExecutionClickCssImpl();

        StepExecutionFabric fabric = new StepExecutionFabricImpl(List.of(sleep, clickCss, clickXpath));

        assertEquals(sleep,fabric.getStepExecutor(StepAction.SLEEP));
        assertEquals(clickXpath,fabric.getStepExecutor(StepAction.CLICK_XPATH));
        assertEquals(clickCss,fabric.getStepExecutor(StepAction.CLICK_CSS));

        assertEquals(sleep,fabric.getStepExecutor(StepAction.SLEEP.getName()));
        assertEquals(clickXpath,fabric.getStepExecutor(StepAction.CLICK_XPATH.getName()));
        assertEquals(clickCss,fabric.getStepExecutor(StepAction.CLICK_CSS.getName()));

    }

    @Test
    public void withoutStepExecutionSleep() {

        StepExecution clickXpath = new StepExecutionClickXpathImpl();
        StepExecution clickCss = new StepExecutionClickCssImpl();

        StepExecutionFabric fabric = new StepExecutionFabricImpl(List.of(clickCss, clickXpath));

        assertThrows(StepExecutionException.class,() ->fabric.getStepExecutor(StepAction.SLEEP));
        assertEquals(clickXpath,fabric.getStepExecutor(StepAction.CLICK_XPATH));
        assertEquals(clickCss,fabric.getStepExecutor(StepAction.CLICK_CSS));

        assertThrows(StepExecutionException.class,() ->fabric.getStepExecutor(StepAction.SLEEP.getName()));
        assertEquals(clickXpath,fabric.getStepExecutor(StepAction.CLICK_XPATH.getName()));
        assertEquals(clickCss,fabric.getStepExecutor(StepAction.CLICK_CSS.getName()));

    }

    @Test
    public void whenIllegalNameOfStepAction() {
        StepExecutionFabric fabric = new StepExecutionFabricImpl(List.of(new StepExecutionSleepImpl(), new StepExecutionClickXpathImpl(), new StepExecutionClickCssImpl()));

        assertThrows(StepExecutionException.class,() ->fabric.getStepExecutor("bla-bla"));
        assertThrows(StepExecutionException.class,() ->fabric.getStepExecutor("SLEEP"));
        assertThrows(StepExecutionException.class,() ->fabric.getStepExecutor("CLICKCSS"));

    }

}