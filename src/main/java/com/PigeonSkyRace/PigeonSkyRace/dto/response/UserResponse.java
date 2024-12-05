package com.PigeonSkyRace.PigeonSkyRace.dto.response;

import com.PigeonSkyRace.PigeonSkyRace.model.RoleEntity;
import com.PigeonSkyRace.PigeonSkyRace.model.User;

public record UserResponse(String email, String role) {
}