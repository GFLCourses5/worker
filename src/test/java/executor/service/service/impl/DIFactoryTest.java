package executor.service.service.impl;

import executor.service.model.WebDriverConfig;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.TasksFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for testing the functionality of the {@code DIFactory} class
 * This class contains unit tests to verify that {@code DIFactory} is working correctly.
 *
 * @author Dima Silenko, Oleksandr Tuleninov
 * @version 01
 */
public class DIFactoryTest {

    @Test
    public void testDIFactorySingleton() {
        DIFactory instance1 = DIFactory.getInstance();
        DIFactory instance2 = DIFactory.getInstance();

        assertEquals(instance1, instance2);
    }

    @Test
    public void testParallelFlowExecutorService() {
        DIFactory diFactory = DIFactory.getInstance();
        var service1 = diFactory.createObject(ParallelFlowExecutorService.class);
        assertNotNull(service1);

        // checking for singleton
        var service2 = diFactory.createObject(ParallelFlowExecutorService.class);
        assertNotNull(service2);
        assertEquals(service1, service2);

        //check that singleton using cache
        var objectFromCache = diFactory.createObject(ParallelFlowExecutorService.class);
        assertEquals(service1, objectFromCache);
    }

    @Test
    public void testAnyClass() {
        DIFactory diFactory = DIFactory.getInstance();
        var service1 = diFactory.createObject(WebDriverConfig.class);
        assertNotNull(service1);

        // checking for singleton
        var service2 = diFactory.createObject(WebDriverConfig.class);
        assertNotNull(service2);
        assertEquals(service1, service2);

        //check that singleton using cache
        var objectFromCache = diFactory.createObject(WebDriverConfig.class);
        assertEquals(service1, objectFromCache);
    }

    @Test
    public void testAnyClassTasksFactory() {
        DIFactory diFactory = DIFactory.getInstance();
        var service1 = diFactory.createObject(TasksFactory.class);
        assertNotNull(service1);

        // checking for singleton
        var service2 = diFactory.createObject(TasksFactory.class);
        assertNotNull(service2);
        assertEquals(service1, service2);

        //check that singleton using cache
        var objectFromCache = diFactory.createObject(TasksFactory.class);
        assertEquals(service1, objectFromCache);
    }
}
