package com.PigeonSkyRace.PigeonSkyRace.dto;

import com.PigeonSkyRace.PigeonSkyRace.model.Pigeon;

import java.time.LocalTime;

public record PigeonsResultsDto(Pigeon pigeon , LocalTime arrivalTime) {
}
