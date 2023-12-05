package com.jwtauthcruddemo.configs;

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

    private static final String URL_PROJECT_FRONT = "http://localhost:4200";
    private static final Long MAX_AGE = 3600L;

    private static final List<String> ALLOWED_HEADERS = List.of(
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.CONTENT_TYPE,
            HttpHeaders.ACCEPT
    );
    private static final List<String> ALLOWED_METHODS = List.of(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()
    );

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = setupCorsConfiguration();

        UrlBasedCorsConfigurationSource sourceBasedConfiguration = getUrlBasedCorsConfigurationSource(corsConfiguration);

        return new CorsFilter(sourceBasedConfiguration);
    }

    private static UrlBasedCorsConfigurationSource getUrlBasedCorsConfigurationSource(CorsConfiguration corsConfiguration) {
        var urlSource = new UrlBasedCorsConfigurationSource();
        urlSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlSource;
    }

    private CorsConfiguration setupCorsConfiguration() {
        var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin(URL_PROJECT_FRONT);
        corsConfiguration.setAllowedHeaders(ALLOWED_HEADERS);
        corsConfiguration.setAllowedMethods(ALLOWED_METHODS);
        corsConfiguration.setMaxAge(MAX_AGE);
        return corsConfiguration;
    }

}
