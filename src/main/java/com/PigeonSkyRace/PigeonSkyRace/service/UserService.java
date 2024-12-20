package com.PigeonSkyRace.PigeonSkyRace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PigeonSkyRace.PigeonSkyRace.Mapper.UserMapper;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.UserRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.UserResponse;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.UsernameAlreadyExistsException;
import com.PigeonSkyRace.PigeonSkyRace.model.User;
import com.PigeonSkyRace.PigeonSkyRace.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KeycloakUserService keycloakUserService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            KeycloakUserService keycloakUserService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.keycloakUserService = keycloakUserService;
    }

    public UserResponse registerUser(UserRequest userRequest) {
        log.info("Registering new user with email: {}", userRequest.email());
        
        // Check if user exists
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new UsernameAlreadyExistsException("Email is already in use.");
        }

        // Create user in Keycloak first
        keycloakUserService.createKeycloakUser(userRequest, userRequest.password());

        // If Keycloak creation successful, create in local database
        User user = userMapper.userRequestToUserEntity(userRequest);
        User savedUser = userRepository.save(user);

        log.info("Successfully registered user with email: {}", savedUser.getEmail());

        return new UserResponse(
            savedUser.getEmail(),
            savedUser.getDoveCote(),
            savedUser.getGpsCoordinates()
        );
    }

    public UserResponse getUserDetails(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.userEntityToUserResponse(user);
    }
}