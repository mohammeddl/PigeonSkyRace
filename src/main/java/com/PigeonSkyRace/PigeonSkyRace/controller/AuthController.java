package com.PigeonSkyRace.PigeonSkyRace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.LoginRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.UserRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.TokenResponse;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.UserResponse;
import com.PigeonSkyRace.PigeonSkyRace.service.AuthenticationService;
import com.PigeonSkyRace.PigeonSkyRace.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operations related to authentication")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user", description = "Register a new user with email, password, and role!!", responses = {
            @ApiResponse(responseCode = "200", description = "User successfully registered", content = @Content(schema = @Schema(implementation = UserResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        log.info("Registering new user with email: {}", request.email());
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @Operation(summary = "Login user", description = "Login with email and password to get access token", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated", content = @Content(schema = @Schema(implementation = TokenResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Processing login for user: {}", request.email());
        return ResponseEntity.ok(authenticationService.login(request));
    }
}