package executor.service.config.bean;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Request interceptor for adding authorization headers to outgoing HTTP requests.
 * <p>
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

    @Value("${webshare.token}")
    private String token;
    @Value("${webshare.url}")
    private String url;

    /**
     * Applies the interceptor to the given request template.
     *
     * @param template The request template to which the interceptor should be applied.
     */
    @Override
    public void apply(RequestTemplate template) {
        if (shouldAddAuthorizationHeader(template)) {
            template.header("Authorization", token);
        }
    }

    private boolean shouldAddAuthorizationHeader(RequestTemplate template) {
        return template.feignTarget().url().contains(url);
    }
}
