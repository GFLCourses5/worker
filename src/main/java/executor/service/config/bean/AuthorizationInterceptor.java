package executor.service.config.bean;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Request interceptor for adding authorization headers to outgoing HTTP requests.
 *
 * This class implements the Spring Cloud OpenFeign `RequestInterceptor` interface
 * to add an "Authorization" header to HTTP requests when the target URL matches
 * a Webshare resource URL.
 * <p>
 *
 * @author Yurii Kotsiuba
 * @version 01
 * @see RequestInterceptor
 */
@Configuration
public class AuthorizationInterceptor implements RequestInterceptor {
    @Value("${PROXY_TOKEN}")
    private String token;
    @Value("https://proxy.webshare.io/api/v2")
    private String url;
    private final String headerName = "Authorization";

    /**
     * Applies the interceptor to the given request template.
     *
     * @param template The request template to which the interceptor should be applied.
     * <p>
     *  *
     *  * @author Yurii Kotsiuba
     *  * @version 01
     */
    @Override
    public void apply(RequestTemplate template) {
        if(shouldAddAuthorizationHeader(template)) {
            template.header(headerName, token);
        }
    }

    private boolean shouldAddAuthorizationHeader(RequestTemplate template) {
        return template.feignTarget().url().contains(url);
    }
}
