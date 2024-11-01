package com.PigeonSkyRace.PigeonSkyRace.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CompetitionDto(String raceName, String releasePointGps, LocalDate departureDate, LocalTime departureTime, double distance, String pigeonIds, List<String> breeders) {
}