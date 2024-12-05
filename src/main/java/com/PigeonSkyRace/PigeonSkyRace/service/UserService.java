package com.PigeonSkyRace.PigeonSkyRace.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PigeonSkyRace.PigeonSkyRace.Mapper.UserMapper;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.UserRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.LoginResponse;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.UserResponse;
import com.PigeonSkyRace.PigeonSkyRace.enums.Role;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.UsernameAlreadyExistsException;
import com.PigeonSkyRace.PigeonSkyRace.model.RoleEntity;
import com.PigeonSkyRace.PigeonSkyRace.model.User;
import com.PigeonSkyRace.PigeonSkyRace.repository.RoleRepository;
import com.PigeonSkyRace.PigeonSkyRace.repository.UserRepository;
import com.PigeonSkyRace.PigeonSkyRace.utils.JWTUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final JWTUtils jwtUtils;

    public UserResponse registerUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new UsernameAlreadyExistsException("Email is already in use.");
        }
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

    public LoginResponse login(UserRequest userRequest) {
        if (!userRepository.existsByEmail(userRequest.email())) {
            throw new RuntimeException("User not found");
        }
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userRequest.email(),
                        userRequest.password()));

        User user = userRepository.findByEmail(userRequest.email()).get();
        String jwtToken = jwtUtils.generateToken(user);
        return userMapper.LoginUserEntityToUserResponse(user, jwtToken);

    }

    public UserResponse updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        RoleEntity newRole = roleRepository.findByRoleName(Role.valueOf(roleName))
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getAuthorities().clear();
        user.setAuthorities(newRole);

        userRepository.save(user);
        return userMapper.userEntityToUserResponse(user);
    }
}
