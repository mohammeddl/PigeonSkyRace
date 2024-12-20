package com.PigeonSkyRace.PigeonSkyRace.config;

import jakarta.annotation.PostConstruct;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KeycloakConfig {
    
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;
    
    @Value("${keycloak.admin.username}")
    private String adminUsername;
    
    @Value("${keycloak.admin.password}")
    private String adminPassword;

    @PostConstruct
    public void init() {
        log.info("Initializing KeycloakConfig with URL: {}", authServerUrl);
    }

    @Bean
    public Keycloak keycloak() {
        log.info("Creating Keycloak admin client with URL: {}", authServerUrl);
        try {
            return KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm("master")
                .clientId("admin-cli")
                .username(adminUsername)
                .password(adminPassword)
                .resteasyClient(new ResteasyClientBuilderImpl()
                    .disableTrustManager()
                    .build())
                .build();
        } catch (Exception e) {
            log.error("Error creating Keycloak instance", e);
            throw new RuntimeException("Failed to create Keycloak instance", e);
        }
    }
}