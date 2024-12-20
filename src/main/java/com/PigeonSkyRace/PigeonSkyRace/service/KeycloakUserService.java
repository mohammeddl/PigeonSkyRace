package com.PigeonSkyRace.PigeonSkyRace.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.PigeonSkyRace.PigeonSkyRace.dto.request.UserRequest;
import com.PigeonSkyRace.PigeonSkyRace.exception.KeycloakException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Service
public class KeycloakUserService {
    
    private final Keycloak keycloakAdmin;
    
    @Value("${keycloak.realm:pigeon-race}")
    private String realm;

    public KeycloakUserService(@Qualifier("keycloakClient") Keycloak keycloakAdmin) {
        this.keycloakAdmin = keycloakAdmin;
    }
    
    @PostConstruct
    public void init() {
        log.info("KeycloakUserService initialized with realm: {}", realm);
        if (keycloakAdmin == null) {
            log.error("Keycloak admin client is null!");
        } else {
            log.info("Keycloak admin client successfully injected");
        }
    }

    public void createKeycloakUser(UserRequest userRequest, String password) {
        try {
            // Create user representation
            UserRepresentation user = new UserRepresentation();
            user.setUsername(userRequest.email());
            user.setEmail(userRequest.email());
            user.setEnabled(true);
            user.setEmailVerified(true);

            // Set password credential
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(false);
            user.setCredentials(Collections.singletonList(credential));

            // Get users resource
            UsersResource usersResource = keycloakAdmin.realm(realm).users();

            // Create user
            Response response = usersResource.create(user);

            if (response.getStatus() != 201) {
                throw new KeycloakException("Failed to create user in Keycloak. Status: " + response.getStatus());
            }

            // Get user id from response
            String userId = getCreatedUserId(response);
            if (userId == null) {
                throw new KeycloakException("User created but ID was not found in response");
            }

            // Assign default role
            assignDefaultRole(userId);

            log.info("Successfully created user in Keycloak with ID: {}", userId);
        } catch (Exception e) {
            log.error("Error creating user in Keycloak", e);
            throw new KeycloakException("Failed to create user in Keycloak: " + e.getMessage());
        }
    }

    private String getCreatedUserId(Response response) {
        String locationHeader = response.getHeaderString("Location");
        if (locationHeader != null) {
            String[] parts = locationHeader.split("/");
            return parts[parts.length - 1];
        }
        return null;
    }

    private void assignDefaultRole(String userId) {
        try {
            // Get default role
            RoleRepresentation userRole = keycloakAdmin.realm(realm)
                    .roles()
                    .get("USER")
                    .toRepresentation();

            // Assign role to user
            keycloakAdmin.realm(realm)
                    .users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .add(Arrays.asList(userRole));
        } catch (Exception e) {
            log.error("Error assigning default role to user", e);
            throw new KeycloakException("Failed to assign default role: " + e.getMessage());
        }
    }

    public void deleteUser(String userId) {
        try {
            Response response = keycloakAdmin.realm(realm)
                    .users()
                    .delete(userId);

            if (response.getStatus() != 204) {
                throw new KeycloakException("Failed to delete user. Status: " + response.getStatus());
            }
        } catch (Exception e) {
            log.error("Error deleting user from Keycloak", e);
            throw new KeycloakException("Failed to delete user: " + e.getMessage());
        }
    }

    public void updateUser(String userId, UserRequest userRequest) {
        try {
            UserRepresentation user = new UserRepresentation();
            user.setEmail(userRequest.email());
            user.setUsername(userRequest.email());

            keycloakAdmin.realm(realm)
                    .users()
                    .get(userId)
                    .update(user);
        } catch (Exception e) {
            log.error("Error updating user in Keycloak", e);
            throw new KeycloakException("Failed to update user: " + e.getMessage());
        }
    }
}