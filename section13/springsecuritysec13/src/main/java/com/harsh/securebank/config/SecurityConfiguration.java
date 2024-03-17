package com.harsh.securebank.config;

import com.harsh.securebank.filter.CsrfCookieFilter;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfiguration {

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler csrfAttributeHandler = new CsrfTokenRequestAttributeHandler();
        csrfAttributeHandler.setCsrfRequestAttributeName("_csrf");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());

        CorsConfigurationSource configurationSource = request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
            corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
            corsConfiguration.setExposedHeaders(List.of("Authorization"));
            return corsConfiguration;
        };

        http.sessionManagement(sessionManagementConfigurer ->
                sessionManagementConfigurer.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS))
            .csrf(csrfConfigurer -> csrfConfigurer.csrfTokenRequestHandler(csrfAttributeHandler)
                .ignoringRequestMatchers("/contact", "/register", "/test/**")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
            .cors(corsConfig -> corsConfig.configurationSource(configurationSource))
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/myAccount").hasRole("USER")
                .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/myCards").hasRole("USER")
                .requestMatchers("/myLoans").hasRole("USER")
                .requestMatchers("/user").authenticated()
                .requestMatchers("/contact", "/notices", "/register", "/test/**").permitAll());

        http.oauth2ResourceServer(resourceServer -> resourceServer.jwt(
            jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));

        return http.build();
    }
}
