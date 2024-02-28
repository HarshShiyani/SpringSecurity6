package com.harsh.securebank.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationLoggingAtFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(AuthoritiesLoggingAfterFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        log.info("Authentication is in progress...");
        chain.doFilter(request, response);
    }
}
