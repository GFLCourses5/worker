package executor.service.config.simpleDI;

import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.simpleDI.DIFactory;
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
    public void testParallelFlowExecutorService() {
        DIFactory diFactory = new DIFactory();
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
}
