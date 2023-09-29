package executor.service.config.properties;

/**
 * Application`s constants.
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
    public static final String COMMONS_CONFIGURATION_PROPERTIES = "web-driver.properties";
    public static final String PROXY_VALIDATOR_PROPERTIES = "proxy-validator.properties";
    public static final String PROXY_VALIDATOR_TARGET_URL = "target.url";
    public static final String PROXY_VALIDATOR_CONNECTION_TIMEOUT = "connection.timeout";
    public static final String WEB_DRIVER = "web-driver.properties";
    public static final String WEB_DRIVER_EXECUTABLE = "webDriver-executable";
    public static final String CHROME_EXECUTABLE = "chrome-executable";
    public static final String CHROME_VERSION = "chrome-version";
    public static final String USER_AGENT = "user-agent";
    public static final String PAGE_LOAD_TIMEOUT = "page-load-timeout";
    public static final String IMPLICITLY_WAIT = "implicitly-wait";
    public static final String PROXY_NETWORK_DEFAULT = "proxy-network-default.json";
    public static final String PROXY_CREDENTIALS_DEFAULT = "proxy-credentials-default.json";
    public static final String PROXY_NETWORKS = "proxy-networks.json";
    public static final String PROXY_CREDENTIALS = "proxy-credentials.json";
    public static final String PROXIES = "/proxy.json";
    public static final String PROXY_DEFAULT = "/proxy-default.json";

}
