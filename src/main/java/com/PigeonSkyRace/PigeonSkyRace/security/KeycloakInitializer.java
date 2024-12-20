package com.PigeonSkyRace.PigeonSkyRace.security;

import jakarta.annotation.PostConstruct;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RealmsResource;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class KeycloakInitializer {

    private final Keycloak keycloakAdmin;

    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakInitializer(Keycloak keycloakAdmin) {
        this.keycloakAdmin = keycloakAdmin;
    }

    @PostConstruct
    public void init() {
        log.info("Starting Keycloak initialization...");
        waitForKeycloak();
        initializeRealm();
        initializeRoles();
    }

    private void waitForKeycloak() {
        int maxRetries = 30;
        int retryCount = 0;
        boolean connected = false;

        while (!connected && retryCount < maxRetries) {
            try {
                keycloakAdmin.serverInfo().getInfo();
                connected = true;
                log.info("Successfully connected to Keycloak server");
            } catch (Exception e) {
                retryCount++;
                log.warn("Waiting for Keycloak to be ready... Attempt {}/{}", retryCount, maxRetries);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while waiting for Keycloak", ie);
                }
            }
        }

        if (!connected) {
            throw new RuntimeException("Could not connect to Keycloak after " + maxRetries + " attempts");
        }
    }

    private void initializeRealm() {
        try {
            RealmsResource realmsResource = keycloakAdmin.realms();
            Optional<RealmRepresentation> existingRealm = realmsResource.findAll().stream()
                .filter(r -> r.getRealm().equals(realm))
                .findFirst();

            if (existingRealm.isEmpty()) {
                log.info("Creating new realm: {}", realm);
                RealmRepresentation newRealm = new RealmRepresentation();
                newRealm.setRealm(realm);
                newRealm.setEnabled(true);
                newRealm.setRegistrationAllowed(true);
                newRealm.setSslRequired("external");
                
                realmsResource.create(newRealm);
                log.info("Successfully created realm: {}", realm);
                
                // Wait a moment for the realm to be fully created
                TimeUnit.SECONDS.sleep(2);
            } else {
                log.info("Realm {} already exists", realm);
            }
        } catch (Exception e) {
            log.error("Error initializing realm", e);
            throw new RuntimeException("Failed to initialize realm: " + e.getMessage(), e);
        }
    }

    private void initializeRoles() {
        try {
            RealmResource realmResource = keycloakAdmin.realm(realm);
            List<String> defaultRoles = List.of("USER", "ADMIN", "ORGANIZER");

            for (String roleName : defaultRoles) {
                try {
                    RoleRepresentation existingRole = null;
                    try {
                        existingRole = realmResource.roles().get(roleName).toRepresentation();
                    } catch (Exception e) {
                        log.debug("Role {} doesn't exist yet", roleName);
                    }

                    if (existingRole == null) {
                        RoleRepresentation role = new RoleRepresentation();
                        role.setName(roleName);
                        role.setDescription("Role for " + roleName);
                        log.info("Creating role: {}", roleName);
                        realmResource.roles().create(role);
                        log.info("Successfully created role: {}", roleName);
                        
                        // Wait a moment between role creations
                        TimeUnit.MILLISECONDS.sleep(500);
                    }
                } catch (Exception e) {
                    log.error("Error creating role: {}", roleName, e);
                }
            }
        } catch (Exception e) {
            log.error("Error initializing roles", e);
            throw new RuntimeException("Failed to initialize roles: " + e.getMessage(), e);
        }
    }
}