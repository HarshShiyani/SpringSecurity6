package com.harsh.securebank.filter;

import com.harsh.securebank.constant.SecurityConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null)
        {
            SecretKey secretKey = Keys.hmacShaKeyFor(
                SecurityConstant.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder().issuer("Secure Bank").subject("JWT Token")
                .claim("username", authentication.getPrincipal())
                .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 30000))
                .signWith(secretKey).compact();

            response.setHeader(SecurityConstant.JWT_HEADER, jwt);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // filter if it's a login request.
        return !request.getServletPath().equals("/user");
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities)
    {
        Set<String> authoritySet = new HashSet<>();
        grantedAuthorities.forEach(grantedAuthority -> authoritySet.add(grantedAuthority.getAuthority()));
        return String.join(",", authoritySet);
    }
}
