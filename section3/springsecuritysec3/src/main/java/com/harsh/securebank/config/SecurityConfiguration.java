package com.harsh.securebank.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests
            .requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans")
            .authenticated()
            .requestMatchers("/contact", "/notices", "/register").permitAll());

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());

        return http.build();
    }

//    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager()
    {
/*
        Approach 1: With default password encoder

        UserDetails adminUser = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("admin")
            .authorities("admin")
            .build();

        UserDetails user1 = User.withDefaultPasswordEncoder()
            .username("user1")
            .password("user1")
            .authorities("read")
            .build();
*/

        UserDetails adminUser = User.withUsername("admin")
            .password("admin")
            .authorities("admin")
            .build();

        UserDetails user1 = User.withUsername("user1")
            .password("user1")
            .authorities("read")
            .build();

        return new InMemoryUserDetailsManager(adminUser, user1);
    }

    @Bean
    public PasswordEncoder noOpPasswordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }
}
