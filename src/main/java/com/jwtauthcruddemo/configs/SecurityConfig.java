package com.jwtauthcruddemo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LoginAuthProvider loginAuthProvider;
    private final LoginAuthenticationEntryPoint loginAuthenticationEntryPoint;

    public SecurityConfig(LoginAuthProvider loginAuthProvider, LoginAuthenticationEntryPoint loginAuthenticationEntryPoint) {
        this.loginAuthProvider = loginAuthProvider;
        this.loginAuthenticationEntryPoint = loginAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.exceptionHandling(this::configLoginAuthenticationEntryPoint);
        httpSecurity.addFilterBefore(new JWTAuthFilter(loginAuthProvider), BasicAuthenticationFilter.class); // Filtro antes do filtro de autenticação básico, porque o JWTAuthFilter desejo que seja primeiro filtro.
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(SecurityConfig::configSessionManagement);
        httpSecurity.authorizeHttpRequests(SecurityConfig::configAuthorizeHttpRequests);
        return httpSecurity.build();
    }

    private static void configAuthorizeHttpRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
        authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST, "/v1/login", "/v1/register")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    private static void configSessionManagement(SessionManagementConfigurer<HttpSecurity> management) {
        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void configLoginAuthenticationEntryPoint(ExceptionHandlingConfigurer<HttpSecurity> customizer) {
        customizer.authenticationEntryPoint(loginAuthenticationEntryPoint);
    }
}
