package com.jwtauthcruddemo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LoginAuthProvider loginAuthProvider;

    public SecurityConfig(LoginAuthProvider loginAuthProvider) {
        this.loginAuthProvider = loginAuthProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                // Filtro antes do filtro de autenticação básico, porque o JWTAuthFilter desejo que seja primeiro filtro.
                .addFilterBefore(new JWTAuthFilter(loginAuthProvider), BasicAuthenticationFilter.class)
                .sessionManagement(SecurityConfig::configSessionManagement)
                .authorizeHttpRequests(SecurityConfig::configAuthorizeHttpRequests);

        return httpSecurity.build();
    }

    private static void configAuthorizeHttpRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
        authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST, "/v1/login", "/v1/register").permitAll()
                .anyRequest().authenticated();
    }

    private static void configSessionManagement(SessionManagementConfigurer<HttpSecurity> management) {
        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
