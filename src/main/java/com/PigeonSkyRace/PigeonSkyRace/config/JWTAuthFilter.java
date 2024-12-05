package com.PigeonSkyRace.PigeonSkyRace.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.PigeonSkyRace.PigeonSkyRace.service.CustomUserDetailsService;
import com.PigeonSkyRace.PigeonSkyRace.utils.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        // basic auth
        if (authHeader.startsWith("Basic")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Bearer token jwt
        if (authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            if (jwtToken != null && !jwtToken.isBlank()) {
                String userEmail = jwtUtils.extractUsername(jwtToken);
                if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

                    if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        securityContext.setAuthentication(token);
                        SecurityContextHolder.setContext(securityContext);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
