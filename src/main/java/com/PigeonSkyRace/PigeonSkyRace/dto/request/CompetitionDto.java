package com.PigeonSkyRace.PigeonSkyRace.dto.request;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CompetitionDto(String raceName, String releasePointGps, String status, Duration duration, LocalDate departureDate, LocalTime departureTime, double distance, List<String> breeders) {
}