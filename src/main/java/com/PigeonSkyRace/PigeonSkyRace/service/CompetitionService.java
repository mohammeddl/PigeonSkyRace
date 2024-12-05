package com.PigeonSkyRace.PigeonSkyRace.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.PigeonSkyRace.PigeonSkyRace.dto.request.CompetitionDto;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NegativeDurationException;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NoCompetitionWasFound;
import com.PigeonSkyRace.PigeonSkyRace.helper.GpsCoordinatesHelper;
import com.PigeonSkyRace.PigeonSkyRace.helper.HaversineFormula;
import com.PigeonSkyRace.PigeonSkyRace.model.*;
import com.PigeonSkyRace.PigeonSkyRace.repository.PigeonsRepository;
import com.PigeonSkyRace.PigeonSkyRace.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PigeonSkyRace.PigeonSkyRace.helper.Validator;
import com.PigeonSkyRace.PigeonSkyRace.repository.CompetitionRepository;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private static final double EARTH_RADIUS = 6371;
    private final CompetitionRepository competitionRepository;

    private final UserRepository breederRepository;

    private final PigeonsRepository pigeonsRepository;

    private final Validator validator;

    // public Competition createCompetition(CompetitionDto competitionDto) {
    //     if (!validator.validateDepartureDate(competitionDto.departureDate())) {
    //         throw new IllegalArgumentException("Departure date cannot be in the session");
    //     }
    //     List<String> breeders = breederRepository.findAll().stream()
    //             .map(breeder -> breeder.toString())
    //             .collect(Collectors.toList());
    //     Competition competition = new Competition();
    //     competition.setRaceName(competitionDto.raceName());
    //     competition.setReleasePointGps(competitionDto.releasePointGps());
    //     competition.setDepartureDate(competitionDto.departureDate());
    //     competition.setDepartureTime(competitionDto.departureTime());
    //     competition.setDistance(competitionDto.distance());
    //     competition.setBreeders(breeders);
    //     competition.setDuration(competitionDto.duration());
    //     competition.setStatus("open");
    //     return competitionRepository.save(competition);
    // }

    // public List<Result> closeCompetition(List<PigeonResults> pigeonsResults) {
    //     String competitionID = pigeonsResults.get(0).getCompetitionID();
    //     Competition competition = competitionRepository.findById(competitionID)
    //             .orElseThrow(() -> new NoCompetitionWasFound("with the following ID :" + competitionID));
    //     LocalTime competitionTime = (LocalTime) competition.getDuration().addTo(competition.getDepartureTime());
    //     competition.setStatus("closed");
    //     competitionRepository.save(competition);
    //     return calcResults(pigeonsResults, competition);
    // }

    // public List<Result> calcResults(List<PigeonResults> pigeonsResultsDtos, Competition competition) {
    //     List<Result> results = new ArrayList<>();
    //     pigeonsResultsDtos.forEach(pigeonsResultsDto -> {
    //         Result result = new Result();
    //         Pigeon pigeon = pigeonsRepository.findById(pigeonsResultsDto.getPigeon()).orElseThrow();
    //         double distance = calcDistance(pigeon, competition);
    //         result.setDistance(distance);
    //         result.setPigeon(pigeon.getRingNumber());
    //         Duration FlightTime = calcFlightTime(pigeonsResultsDto, competition);
    //         result.setFlightTime(FlightTime);
    //         double adjustCoeff = calcAdjustmnetCoeff(distance, competition);
    //         result.setAdjustmentCoefficient(adjustCoeff);
    //         double speed = calcSpeed(distance, FlightTime, adjustCoeff);
    //         result.setSpeed(speed);
    //         results.add(result);
    //     });
    //     double speedAverage = calcSpeedAverage(results);
    //     results.stream().forEach(result -> {
    //         double points = calcPoints(speedAverage, result.getSpeed());
    //         result.setPoints(points);
    //     });
    //     rankingService.saveRankings(competition, results);
    //     return results;
    // }

    // public double calcDistance(Pigeon pigeon, Competition competition) {
    //     Breeder breeder = breederRepository.findByPigeonId(pigeon.getBreeder()).orElseThrow(
    //             () -> new NoCompetitionWasFound("Breeder not found for pigeon ID: " + pigeon.getBreeder()));
    //     double arrivalLongitude = GpsCoordinatesHelper.getLongitude(competition.getReleasePointGps());
    //     double arrivalLatitude = GpsCoordinatesHelper.getLatitude(competition.getReleasePointGps());
    //     double releaseLongitude = GpsCoordinatesHelper.getLongitude(breeder.getGpsCoordinates());
    //     double releaseLatitude = GpsCoordinatesHelper.getLatitude(breeder.getGpsCoordinates());
    //     double dLat = Math.toRadians((arrivalLatitude - releaseLatitude));
    //     double dLong = Math.toRadians((arrivalLongitude - releaseLongitude));
    //     releaseLatitude = Math.toRadians(releaseLatitude);
    //     arrivalLatitude = Math.toRadians(arrivalLatitude);
    //     double a = HaversineFormula.haversine(dLat)
    //             + Math.cos(releaseLatitude) * Math.cos(arrivalLatitude) * HaversineFormula.haversine(dLong);
    //     double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    //     return EARTH_RADIUS * c;
    // }

    // public Duration calcFlightTime(PigeonResults pigeonsResults, Competition competition) {
    //     LocalTime arrivalTime = pigeonsResults.getArrivalTime();
    //     LocalTime departureTime = competition.getDepartureTime();
    //     if (arrivalTime.isAfter(departureTime)) {
    //         Duration flightDuration = Duration.between(departureTime, arrivalTime);
    //         return flightDuration;
    //     } else {
    //         throw new NegativeDurationException(
    //                 "arrival time :" + arrivalTime + "cannot be after departure time:" + departureTime);
    //     }
    // }

    // public double calcSpeed(double distance, Duration flightDuration, double adjustementCoeff) {
    //     return (distance / flightDuration.toHours()) * adjustementCoeff;
    // }

    // public double calcAdjustmnetCoeff(double pigeonDistance, Competition competition) {
    //     return competition.getDistance() / pigeonDistance;
    // }

    // public double calcSpeedAverage(List<Result> results) {
    //     double totalSpeed = results.stream().mapToDouble(result -> result.getSpeed()).sum();
    //     return totalSpeed / results.size();
    // }

    // public double calcPoints(double averageSpeed, double speed) {
    //     return (speed / averageSpeed) * 100;
    // }

    // public double calcDistance(String releasePoint, String arrivalPoint) {
    //     double arrivalLongitude = GpsCoordinatesHelper.getLongitude(arrivalPoint);
    //     double arrivalLatitude = GpsCoordinatesHelper.getLatitude(arrivalPoint);
    //     double releaseLongitude = GpsCoordinatesHelper.getLongitude(releasePoint);
    //     double releaseLatitude = GpsCoordinatesHelper.getLatitude(releasePoint);
    //     double dLat = Math.toRadians((arrivalLatitude - releaseLatitude));
    //     double dLong = Math.toRadians((arrivalLongitude - releaseLongitude));
    //     releaseLatitude = Math.toRadians(releaseLatitude);
    //     arrivalLatitude = Math.toRadians(arrivalLatitude);
    //     double a = HaversineFormula.haversine(dLat)
    //             + Math.cos(releaseLatitude) * Math.cos(arrivalLatitude) * HaversineFormula.haversine(dLong);
    //     double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    //     return EARTH_RADIUS * c;
    // }

}
