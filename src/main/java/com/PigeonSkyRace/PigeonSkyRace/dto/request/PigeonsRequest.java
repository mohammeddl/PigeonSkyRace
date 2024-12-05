package com.PigeonSkyRace.PigeonSkyRace.dto.request;

import java.time.LocalDate;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

public record PigeonsRequest(
                @NotBlank(message = "should be not empty") String sex,
                @PastOrPresent(message = "should be not date future") LocalDate birthDate,
                @NotBlank(message = "not empty") String color) {
}
