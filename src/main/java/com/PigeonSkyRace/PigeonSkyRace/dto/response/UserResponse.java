package com.PigeonSkyRace.PigeonSkyRace.dto.response;

public record UserResponse(
    String email,
    String doveCote,
    String gpsCoordinates
) {}