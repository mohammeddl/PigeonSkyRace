package com.PigeonSkyRace.PigeonSkyRace.dto.response;

import java.time.LocalDate;

import com.PigeonSkyRace.PigeonSkyRace.dto.embeded.UserDetailsResponse;

public record PigeonsResponse(Long ringNumber,
        String sex,
        String color,
        LocalDate birthDate,
        UserDetailsResponse user) {
    
}
