package com.PigeonSkyRace.PigeonSkyRace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PigeonSkyRace.PigeonSkyRace.dto.response.UserResponse;
import com.PigeonSkyRace.PigeonSkyRace.dto.update.UserUpdate;
import com.PigeonSkyRace.PigeonSkyRace.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "User API", description = "Operations related to user management")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable Long userId, @RequestBody UserUpdate userUpdate) {
        UserResponse updatedUser = userService.updateUser(userId, userUpdate);
        return ResponseEntity.ok(updatedUser);
    }

}