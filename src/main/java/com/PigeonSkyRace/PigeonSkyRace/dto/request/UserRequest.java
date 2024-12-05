package com.PigeonSkyRace.PigeonSkyRace.dto.request;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @Email(message = "Username is required") String email,
        @NotBlank(message = "Password is required") String password,
        String doveCote,
        String gpsCoordinates,
        List<String> pigeonIds) {
}
