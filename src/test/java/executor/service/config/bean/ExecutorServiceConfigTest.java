package executor.service.config.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains unit tests for the {@link ExecutorServiceConfig} class.
 * <p>
 *
 * @author Dima Silenko, Oleksandr Tuleninov
 * @version 01
 */
public class ExecutorServiceConfigTest {

    private ExecutorServiceConfig config;

    @BeforeEach
    public void setUp() {
        this.config = new ExecutorServiceConfig();
    }

    @Test
    public void testThreadPoolExecutor() {
        var executorService = config.threadPoolExecutor(4, 8, 60L);

        int corePoolSize = ((ThreadPoolExecutor) executorService).getCorePoolSize();
        int maximumPoolSize = ((ThreadPoolExecutor) executorService).getMaximumPoolSize();
        long keepAliveTime = ((ThreadPoolExecutor) executorService).getKeepAliveTime(TimeUnit.SECONDS);

        assertEquals(4, corePoolSize, "Core pool size should be 4");
        assertEquals(8, maximumPoolSize, "Maximum pool size should be 8");
        assertEquals(60L, keepAliveTime, "Keep-alive time should be 60 seconds");
    }
}
