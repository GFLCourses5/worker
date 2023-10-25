package executor.service.config.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("#{'${white.list.ip}'.split(',')}")
    private List<String> whiteList;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(this::setWhiteList);
        return http.build();
    }

    private void setWhiteList(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request) {
        List<IpAddressMatcher> matchers = whiteList.stream().map(IpAddressMatcher::new).toList();

        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = request.anyRequest();
            authorizedUrl.access(
                    (authentication, context) -> new AuthorizationDecision(
                            matchers.stream().anyMatch(s -> s.matches(context.getRequest()))
                    ));
    }

}