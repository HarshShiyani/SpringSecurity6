package com.harsh.securebank.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.harsh.securebank.filter.CsrfCookieFilter;
import java.util.Collections;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        CorsConfigurationSource configurationSource = request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
            corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
            return corsConfiguration;
        };

        http.csrf(csrfConfigurer -> csrfConfigurer.csrfTokenRequestHandler(csrfAttributeHandler)
                .ignoringRequestMatchers("/contact", "/register")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
            .cors(corsConfig -> corsConfig.configurationSource(configurationSource))
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans")
                .authenticated()
                .requestMatchers("/contact", "/notices", "/register").permitAll());

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder noOpPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
