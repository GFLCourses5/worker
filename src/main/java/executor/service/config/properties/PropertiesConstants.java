package executor.service.config.properties;

/**
 * Class for application`s constants.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * */
public final class PropertiesConstants {

    private PropertiesConstants() {
        throw new AssertionError("non-instantiable class");
    }

    public static final String THREAD_POOL_PROPERTIES = "thread-pool.properties";
    public static final String CORE_POOL_SIZE = "core.pool.size";
    public static final String KEEP_ALIVE_TIME = "keep.alive.time";
    public static final String PROXY_VALIDATOR_TARGET_URL = "https://www.google.com";
    public static final Integer PROXY_VALIDATOR_CONNECTION_TIMEOUT = 10000;

    public static final String SCENARIOS = "scenarios.json";

    public static final String WEB_DRIVER = "web-driver.properties";
    public static final String WEB_DRIVER_EXECUTABLE = "webDriver-executable";
    public static final String USER_AGENT = "user-agent";
    public static final String PAGE_LOAD_TIMEOUT = "page-load-timeout";
    public static final String IMPLICITLY_WAIT = "implicitly-wait";

    public static final String PROXY_CREDENTIAL = "proxy-credential.json";
    public static final String PROXY_NETWORK = "proxy-network.json";
    public static final String PROXY_NETWORK_DEFAULT = "proxy-network-default.json";
    public static final String PROXY_CREDENTIALS_DEFAULT = "proxy-credentials-default.json";
    public static final String WEB_DRIVER = "web-driver.properties";
    public static final String WEBDRIVER_EXECUTABLE = "webDriver-executable";
    public static final String USER_AGENT = "user-agent";
    public static final String PAGE_LOAD_TIMEOUT = "page-load-timeout";
    public static final String IMPLICITLY_WAIT = "implicitly-wait";
}
