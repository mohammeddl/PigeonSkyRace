package com.PigeonSkyRace.PigeonSkyRace.security;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeycloakInitializer implements InitializingBean {
    private static final int MAX_RETRIES = 10;
    private static final long RETRY_DELAY_MS = 5000; // 5 seconds

    @Value("${keycloak.auth-server-url:http://keycloak:8080}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm:master}")
    private String masterRealm;

    @Value("${keycloak.admin.username:admin}")
    private String adminUsername;

    @Value("${keycloak.admin.password:admin}")
    private String adminClientId;

    @Value("${keycloak.admin.password:admin}")
    private String adminPassword;

    private Keycloak keycloakAdmin;

    @Override
    public void afterPropertiesSet() {
        int retryCount = 0;
        while (retryCount < MAX_RETRIES) {
            try {
                initializeKeycloak();
                
                return; 
            } catch (Exception e) {
                retryCount++;
                if (retryCount == MAX_RETRIES) {
                    throw new RuntimeException("Failed to initialize Keycloak after " + MAX_RETRIES + " attempts", e);
                }
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while waiting to retry Keycloak initialization", ie);
                }
            }
        }
    }

    private void initializeKeycloak() {
        this.keycloakAdmin = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(masterRealm)
                .username(adminUsername)
                .password(adminPassword)
                .clientId(adminClientId)
                .build();
    }

   
}