package executor.service.config;

import executor.service.model.ThreadPoolConfig;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory {
    private static final int MAXIMUM_POOL_SIZE = 16;
    private static ThreadPoolExecutor executor;

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        if (executor == null) {
            executor = configureThreadPoolExecutor();
        }
        return executor;
    }

    private static ThreadPoolExecutor configureThreadPoolExecutor() {
        ThreadPoolConfig threadPoolConfig = ThreadPoolConfigFactory.getPoolConfig();
        return new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                MAXIMUM_POOL_SIZE,
                threadPoolConfig.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }
}
