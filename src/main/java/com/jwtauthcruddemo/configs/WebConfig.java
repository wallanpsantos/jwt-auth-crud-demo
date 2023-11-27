package com.jwtauthcruddemo.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig {

    private static final String URL_PROJECT_FRONT = "http://localhost:4200"; // Angular

    private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102; // Order Bean initialization

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {

        CorsConfiguration corsConfiguration = setupCorsConfiguration();

        var urlSource = new UrlBasedCorsConfigurationSource();
        urlSource.registerCorsConfiguration("/**", corsConfiguration);

        var registrationBean = new FilterRegistrationBean<>(new CorsFilter(urlSource));
        registrationBean.setOrder(CORS_FILTER_ORDER);

        return registrationBean;
    }

    private CorsConfiguration setupCorsConfiguration() {
        var corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin(URL_PROJECT_FRONT);
        corsConfiguration.setAllowedHeaders(List.of(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));
        corsConfiguration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()
        ));
        corsConfiguration.setMaxAge(MAX_AGE);
        return corsConfiguration;
    }
}
