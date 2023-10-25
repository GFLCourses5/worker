package executor.service.security;


import jakarta.servlet.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@EnableWebSecurity
public class SecurityTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                    .build();
    }

    @Test
    public void isPermittedAddress() throws Exception {
        mvc.perform(get("/").remoteAddress("192.168.0.1")).andExpect(status().is(404));
        mvc.perform(get("/").remoteAddress("192.168.0.3")).andExpect(status().is(404));
        mvc.perform(get("/").remoteAddress("192.168.0.5")).andExpect(status().is(404));
        mvc.perform(get("/").remoteAddress("192.168.0.6")).andExpect(status().is(404));
    }

    @Test
    public void isNotPermittedAddress() throws Exception {
        mvc.perform(get("/").remoteAddress("192.168.0.2")).andExpect(status().is(403));
        mvc.perform(get("/").remoteAddress("192.168.0.10")).andExpect(status().is(403));
        mvc.perform(get("/").remoteAddress("192.168.0.11")).andExpect(status().is(403));
        mvc.perform(get("/").remoteAddress("192.168.0.12")).andExpect(status().is(403));
    }


    @Configuration
    @EnableWebMvc
    @EnableWebSecurity
    static class Config{

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            List<IpAddressMatcher> whiteList = getWhiteList();
            http.authorizeHttpRequests(request ->
                    request.requestMatchers("/").access(
                            (authentication, context)-> new AuthorizationDecision(
                                    whiteList.stream().anyMatch(s -> s.matches(context.getRequest()))
                            ))
            );
            return http.build();
        }

        private List<IpAddressMatcher> getWhiteList() {
            return List.of(new IpAddressMatcher("192.168.0.1"), new IpAddressMatcher("192.168.0.3"), new IpAddressMatcher("192.168.0.5"), new IpAddressMatcher("192.168.0.6"));
        }

    }

}
