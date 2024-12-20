package com.PigeonSkyRace.PigeonSkyRace.config;

import jakarta.annotation.PostConstruct;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
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
    private String serverUrl;
    
    @Value("${keycloak.realm}")
    private String realm;
    
    @Value("${keycloak.admin.username}")
    private String adminUsername;
    
    @Value("${keycloak.admin.password}")
    private String adminPassword;
    
    @Value("${keycloak.client-id}")
    private String clientId;

    @PostConstruct
    public void init() {
        log.info("KeycloakConfig initialized with URL: {}", serverUrl);
        log.info("Realm: {}", realm);
        log.info("ClientId: {}", clientId);
        log.info("Admin Username: {}", adminUsername);
    }

    @Bean(name = "keycloakClient")
    public Keycloak keycloak() {
        log.info("Creating Keycloak instance");
        
        ResteasyClient client = ((ResteasyClientBuilder) ResteasyClientBuilder.newBuilder())
            .disableTrustManager()
            .build();
    
        return KeycloakBuilder.builder()
            .serverUrl(serverUrl)
            .realm(realm)
            .grantType(OAuth2Constants.PASSWORD)
            .username(adminUsername)
            .password(adminPassword)
            .clientId(clientId)
            .resteasyClient(client)
            .build();
    }
    
}