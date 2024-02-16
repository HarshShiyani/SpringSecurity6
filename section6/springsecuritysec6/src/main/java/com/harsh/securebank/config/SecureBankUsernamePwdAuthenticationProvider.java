package com.harsh.securebank.config;

import com.harsh.securebank.entity.Customer;
import com.harsh.securebank.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecureBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        String username = authentication.getPrincipal().toString();
        String rawPassword = authentication.getCredentials().toString();

        Customer customer = customerRepository.findByEmail(username);
        if (customer == null) {
            throw new BadCredentialsException(username + " is not registered in the system");
        }

        boolean matchPassword = passwordEncoder.matches(rawPassword, customer.getPwd());
        if (!matchPassword) {
            throw new BadCredentialsException("Invalid password");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(customer.getRole()));

        return new UsernamePasswordAuthenticationToken(username, rawPassword, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
