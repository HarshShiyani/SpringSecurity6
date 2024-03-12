package com.harsh.securebank.filter;

import com.harsh.securebank.constant.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(SecurityConstant.JWT_HEADER);
        if (jwtToken != null) {
            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(
                    SecurityConstant.JWT_KEY.getBytes(StandardCharsets.UTF_8));

                Claims payload = (Claims) Jwts.parser().verifyWith(secretKey).build().parse(jwtToken)
                    .getPayload();

                String username = (String) payload.get("username");
                String authorities = (String) payload.get("authorities");
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token : " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/user");
    }
}
