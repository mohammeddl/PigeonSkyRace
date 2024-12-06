package com.PigeonSkyRace.PigeonSkyRace.config;

import org.springframework.stereotype.Component;

import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        System.err.println("Unauthorized access attempt to: " + request.getRequestURI());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.UNAUTHORIZED.value(),
                "Authentication failed. Please check your credentials.");
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }

}
