package com.harsh.securebank.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthoritiesLoggingAfterFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(AuthoritiesLoggingAfterFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null)
            log.info("User {} is successfully authenticated and has the authorities {}", authentication.getPrincipal(), authentication.getAuthorities());

        chain.doFilter(request, response);
    }
}
