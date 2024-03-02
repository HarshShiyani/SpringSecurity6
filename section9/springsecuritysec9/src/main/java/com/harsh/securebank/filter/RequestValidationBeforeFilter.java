package com.harsh.securebank.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;


public class RequestValidationBeforeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        System.out.println("***************************************************");
        System.out.println("********* Before BasicAuthenticationFilter ********");
        System.out.println("***************************************************");

        chain.doFilter(request, response);
    }
}
