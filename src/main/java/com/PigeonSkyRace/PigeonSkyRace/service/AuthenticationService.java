package com.PigeonSkyRace.PigeonSkyRace.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.LoginRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.TokenResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationService {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource:pigeon-race-client}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String secret;

    private final RestTemplate restTemplate;

    public AuthenticationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TokenResponse login(LoginRequest loginRequest) {
        log.info("Attempting login for user: {}", loginRequest.email());
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token", authServerUrl, realm);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_secret", secret);
        formData.add("client_id", clientId);
        formData.add("username", loginRequest.email());
        formData.add("password", loginRequest.password());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            log.debug("Sending token request to: {}", tokenUrl);
            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                tokenUrl, 
                request, 
                TokenResponse.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("Login successful for user: {}", loginRequest.email());
                return response.getBody();
            }
            
            log.error("Login failed. Status: {}", response.getStatusCode());
            throw new RuntimeException("Authentication failed");
        } catch (Exception e) {
            log.error("Login failed", e);
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }
}