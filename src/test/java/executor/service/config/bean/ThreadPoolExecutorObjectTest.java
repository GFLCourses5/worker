package executor.service.config.bean;

import executor.service.config.properties.PropertiesConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

import static executor.service.config.properties.PropertiesConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for testing the functionality of the {@code ThreadPoolExecutorObject} class.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ThreadPoolExecutorObject
 */
public class ThreadPoolExecutorObjectTest {

    @Test
    public void testThreadPoolExecutor() {
        var propertiesConfig = Mockito.mock(PropertiesConfig.class);

        var properties = new Properties();
        properties.setProperty(CORE_POOL_SIZE, "2");
        properties.setProperty(KEEP_ALIVE_TIME, "5");
        properties.setProperty(MAXIMUM_POOL_SIZE, "5");

        when(propertiesConfig.getProperties("thread-pool.properties")).thenReturn(properties);

        var executorObject = new ThreadPoolExecutorObject(propertiesConfig);
        var executorService = executorObject.threadPoolExecutor();

        assertEquals(ThreadPoolExecutor.class, executorService.getClass());
    }
}
