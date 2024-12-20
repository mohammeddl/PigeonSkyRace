package com.PigeonSkyRace.PigeonSkyRace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PigeonSkyRace.PigeonSkyRace.Mapper.UserMapper;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.UserRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.UserResponse;
import com.PigeonSkyRace.PigeonSkyRace.enums.Role;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.UsernameAlreadyExistsException;
import com.PigeonSkyRace.PigeonSkyRace.model.RoleEntity;
import com.PigeonSkyRace.PigeonSkyRace.model.User;
import com.PigeonSkyRace.PigeonSkyRace.repository.RoleRepository;
import com.PigeonSkyRace.PigeonSkyRace.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final KeycloakUserService keycloakUserService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            KeycloakUserService keycloakUserService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.keycloakUserService = keycloakUserService;
    }

    public UserResponse registerUser(UserRequest userRequest) {
        // Check if user exists
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new UsernameAlreadyExistsException("Email is already in use.");
        }

        // Create user in Keycloak first
        keycloakUserService.createKeycloakUser(userRequest, userRequest.password());

        // If Keycloak creation successful, create in local database
        RoleEntity userRole = roleRepository.findByRoleName(Role.USER)
                .orElseThrow(() -> new RuntimeException("Role 'USER' not found"));

        User user = userMapper.userRequestToUserEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setAuthorities(userRole);
        userRepository.save(user);

        String roleName = user.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .findFirst()
                .orElse("UNKNOWN");

        return new UserResponse(user.getEmail(), roleName);
    }

    public UserResponse getUserDetails(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.userEntityToUserResponse(user);
    }
}