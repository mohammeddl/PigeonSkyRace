package com.PigeonSkyRace.PigeonSkyRace.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.FORBIDDEN.value(),
                "You do not have permission to access this resource");
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}
