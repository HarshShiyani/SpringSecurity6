package com.harsh.SpringSecOAuth2Github.config;

import com.nimbusds.openid.connect.sdk.federation.registration.ClientRegistrationType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecOAuth2GithubConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
            authorizeRequest -> authorizeRequest.anyRequest().authenticated())
            .oauth2Login(Customizer.withDefaults());

        return http.build();
    }

/*

    => We don't need to specify bean here
        because the client id and client secret has been provided via application.property

    @Bean
    InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository()
    {
        ClientRegistration githubOAuth2Provider = CommonOAuth2Provider.GITHUB.getBuilder("github")
            .clientId("f8076d91dd2f20c0d4b9")
            .clientSecret("80eee7fd5d48e8b2af7e8caa8d5725dcf4985cd9")
            .build();

        return new InMemoryClientRegistrationRepository(githubOAuth2Provider);
    }
    */
}
