package com.PigeonSkyRace.PigeonSkyRace.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.UserRequest;
import com.PigeonSkyRace.PigeonSkyRace.exception.KeycloakException;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class KeycloakUserService {
    
    private final Keycloak keycloakAdmin;
    
    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakUserService(Keycloak keycloakAdmin) {
        this.keycloakAdmin = keycloakAdmin;
    }

    @PostConstruct
    public void init() {
        log.info("KeycloakUserService initialized with realm: {}", realm);
        createRealmRoles();
    }

    private void createRealmRoles() {
        try {
            List<String> roles = List.of("USER", "ADMIN", "ORGANIZER");
            for (String roleName : roles) {
                if (!roleExists(roleName)) {
                    RoleRepresentation role = new RoleRepresentation();
                    role.setName(roleName);
                    keycloakAdmin.realm(realm).roles().create(role);
                    log.info("Created role: {}", roleName);
                }
            }
        } catch (Exception e) {
            log.error("Error creating roles", e);
        }
    }

    private boolean roleExists(String roleName) {
        try {
            return keycloakAdmin.realm(realm).roles().list().stream()
                .anyMatch(role -> role.getName().equals(roleName));
        } catch (Exception e) {
            log.error("Error checking role existence", e);
            return false;
        }
    }

    public void createKeycloakUser(UserRequest userRequest, String password) {
        log.info("Attempting to create user in Keycloak: {}", userRequest.email());
        try {
            // First check if user exists
            List<UserRepresentation> existingUsers = keycloakAdmin.realm(realm)
                    .users()
                    .searchByEmail(userRequest.email(), true);

            if (!existingUsers.isEmpty()) {
                log.warn("User with email {} already exists in Keycloak", userRequest.email());
                throw new KeycloakException("User with this email already exists");
            }

            UserRepresentation user = new UserRepresentation();
            user.setUsername(userRequest.email());
            user.setEmail(userRequest.email());
            user.setEnabled(true);
            user.setEmailVerified(true);

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(false);
            user.setCredentials(Collections.singletonList(credential));

            log.debug("Getting users resource for realm: {}", realm);
            UsersResource usersResource = keycloakAdmin.realm(realm).users();

            Response response = usersResource.create(user);
            log.debug("User creation response status: {}", response.getStatus());

            if (response.getStatus() == 409) {
                throw new KeycloakException("User with this email already exists");
            } else if (response.getStatus() != 201) {
                throw new KeycloakException("Failed to create user in Keycloak. Status: " + response.getStatus());
            }

            String userId = getCreatedUserId(response);
            if (userId == null) {
                throw new KeycloakException("User created but ID was not found in response");
            }

            // Use the role from the request
            String roleName = userRequest.role().name();
            log.info("Assigning role {} to user {}", roleName, userId);
            assignRole(userId, roleName);

            log.info("Successfully created user in Keycloak with ID: {}", userId);
        } catch (KeycloakException e) {
            throw e;
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

    private void assignRole(String userId, String roleName) {
        try {
            log.debug("Getting role {} for realm {}", roleName, realm);
            RoleRepresentation role = keycloakAdmin.realm(realm)
                    .roles()
                    .get(roleName)
                    .toRepresentation();

            if (role == null) {
                throw new KeycloakException("Role '" + roleName + "' not found");
            }

            log.debug("Assigning role {} to user {}", roleName, userId);
            keycloakAdmin.realm(realm)
                    .users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .add(Collections.singletonList(role));

            log.info("Successfully assigned role {} to user {}", roleName, userId);
        } catch (Exception e) {
            log.error("Error assigning role {} to user {}", roleName, userId, e);
            throw new KeycloakException("Failed to assign role: " + e.getMessage());
        }
    }
}