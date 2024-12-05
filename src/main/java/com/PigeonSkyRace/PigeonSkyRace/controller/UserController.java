package com.PigeonSkyRace.PigeonSkyRace.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PigeonSkyRace.PigeonSkyRace.dto.request.UserRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.LoginResponse;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.UserResponse;
import com.PigeonSkyRace.PigeonSkyRace.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "User API", description = "Operations related to user management")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.registerUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid UserRequest userRequest) {
        LoginResponse loginResponse = userService.login(userRequest);
        return ResponseEntity.ok(loginResponse);
    }

}