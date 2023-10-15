package executor.service.config.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A test class for the {@code ThreadPoolExecutorConfig} class. This class tests the creation
 * of an {@link ExecutorService} by verifying that it is configured with the specified
 * core pool size, maximum pool size, and keep-alive time.
 * <p>
 *
 * @author Dima Silenko, Oleksandr Tuleninov
 * @version 01
 */
public class BeanConfigurationTest {

    private BeanConfiguration config;

    @BeforeEach
    public void setUp() {
        this.config = new BeanConfiguration();
    }

    @Test
    public void testThreadPoolExecutor() {
        var executorService = config.threadPoolExecutor(4, 8, 60L);

        assertEquals(4, ((ThreadPoolExecutor) executorService).getCorePoolSize());
        assertEquals(8, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
        assertEquals(60L, ((ThreadPoolExecutor) executorService).getKeepAliveTime(TimeUnit.SECONDS));
    }

    @Test
    public void testProxyConfigHolder() {
        var proxy = config.proxyConfigHolder();

        assertNotNull(proxy);
    }
}
