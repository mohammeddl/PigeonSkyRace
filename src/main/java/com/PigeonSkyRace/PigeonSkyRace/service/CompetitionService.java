package com.PigeonSkyRace.PigeonSkyRace.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.PigeonSkyRace.PigeonSkyRace.dto.PigeonsResultsDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.ResultDto;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.CompetitionNotFinishedException;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NegativeDurationException;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NoCompetitionWasFound;
import com.PigeonSkyRace.PigeonSkyRace.helper.GpsCoordinatesHelper;
import com.PigeonSkyRace.PigeonSkyRace.helper.HaversineFormula;
import com.PigeonSkyRace.PigeonSkyRace.model.Pigeon;
import com.PigeonSkyRace.PigeonSkyRace.model.Result;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.PigeonSkyRace.PigeonSkyRace.dto.CompetitionDto;
import com.PigeonSkyRace.PigeonSkyRace.helper.Validator;
import com.PigeonSkyRace.PigeonSkyRace.model.Competition;
import com.PigeonSkyRace.PigeonSkyRace.repository.BreederRepository;
import com.PigeonSkyRace.PigeonSkyRace.repository.CompetitionRepository;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private BreederRepository breederRepository;

    @Autowired
    private Validator validator;
    private static final double EARTH_RADIUS = 6371 ;
    public Competition createCompetition(CompetitionDto competitionDto) {
        if(!validator.validateDepartureDate(competitionDto.departureDate())) {
            throw new IllegalArgumentException("Departure date cannot be in the session");
        }
        List<String> breeders = breederRepository.findAll().stream()
                .map(breeder -> breeder.toString())
                .collect(Collectors.toList());
        Competition competition = new Competition();
        competition.setRaceName(competitionDto.raceName());
        competition.setReleasePointGps(competitionDto.releasePointGps());
        competition.setDepartureDate(competitionDto.departureDate());
        competition.setDepartureTime(competitionDto.departureTime());
        competition.setDistance(competitionDto.distance());
        competition.setBreeders(breeders);
        competition.setDuration(competitionDto.duration());
        competition.setStatus("open");
        return competitionRepository.save(competition);
    }
    public List<PigeonsResultsDto> closeCompetition(List<PigeonsResultsDto> pigeonsResultsDtos , String competitionID) {
        Competition competition = competitionRepository.findById(competitionID).orElseThrow(()->new NoCompetitionWasFound("with the following ID :"+competitionID));
        LocalTime competitionTime = (LocalTime) competition.getDuration().addTo(competition.getDepartureTime());
//        if (ResultsDtos.stream().allMatch(resultDto ->
//                validator.validateString(resultDto.pigeon())
//                        &&validator.validateDouble(resultDto.distance())
//                        &&validator.validateDouble(resultDto.flightTime())
//                        &&validator.validateDouble(resultDto.points())
//                        &&validator.validateDouble(resultDto.speed()))) {
//            if (!competitionTime.equals(LocalTime.now())){
//                throw new CompetitionNotFinishedException("Competition" + competition.getRaceName() + "have not finished yet");
//            }
//            List<Result> results = new ArrayList<>();
//            ResultsDtos.forEach(resultDto -> {results.add(new Result(resultDto.pigeon(),resultDto.distance() , resultDto.flightTime(),
//                    resultDto.speed() , resultDto.adjustmentCoefficient(), resultDto.points()));});
//
//        }
    }

    public List<Result> calcResults(List<PigeonsResultsDto> pigeonsResultsDtos , Competition competition) {
        List<Result> results = new ArrayList<>();
        pigeonsResultsDtos.forEach(pigeonsResultsDto -> {
            Result result = new Result();
            double distance = calcDistance(pigeonsResultsDto.pigeon(), competition);
            result.setDistance(distance);
            result.setPigeon(pigeonsResultsDto.pigeon().getRingNumber());
            Duration FlightTime = calcFlightTime(pigeonsResultsDto , competition);
            result.setFlightTime(FlightTime);
            double adjustCoeff = calcAdjustmnetCoeff(distance , competition);
            result.setAdjustmentCoefficient(adjustCoeff);
            double speed = calcSpeed(distance , FlightTime , adjustCoeff);
            result.setSpeed(speed);
            results.add(result);
        });
        double speedAverage = calcSpeedAverage(results);
        results.stream().forEach(result -> {
            double points = calcPoints(speedAverage ,result.getSpeed());
            result.setPoints(points);
        });
        return results;
    }
    public double calcDistance(Pigeon pigeon , Competition competition){
        double arrivalLongitude = GpsCoordinatesHelper.getLongitude(competition.getReleasePointGps());
        double arrivalLatitude = GpsCoordinatesHelper.getLatitude(competition.getReleasePointGps());
        double releaseLongitude = GpsCoordinatesHelper.getLongitude(pigeon.getBreeder().getGpsCoordinates());
        double releaseLatitude = GpsCoordinatesHelper.getLatitude(pigeon.getBreeder().getGpsCoordinates());
        double dLat = Math.toRadians((arrivalLatitude - releaseLatitude));
        double dLong = Math.toRadians((arrivalLongitude - releaseLongitude));
        releaseLatitude = Math.toRadians(releaseLatitude);
        arrivalLatitude = Math.toRadians(arrivalLatitude);
        double a = HaversineFormula.haversine(dLat) + Math.cos(releaseLatitude) * Math.cos(arrivalLatitude) * HaversineFormula.haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
    public Duration calcFlightTime(PigeonsResultsDto pigeonsResultsDto , Competition competition) {
        LocalTime arrivalTime = pigeonsResultsDto.arrivalTime();
        LocalTime departureTime = competition.getDepartureTime();
        if (arrivalTime.isAfter(departureTime)) {
        Duration flightDuration = Duration.between(departureTime, arrivalTime);
        return flightDuration ;
        }else{
            throw new NegativeDurationException("arrival time :" + arrivalTime + "cannot be after departure time:" + departureTime);
        }
    }
    public double calcSpeed(double distance , Duration flightDuration , double adjustementCoeff) {
        return (distance/flightDuration.toMinutes()) * adjustementCoeff ;
    }
    public double calcAdjustmnetCoeff(double pigeonDistance ,Competition competition ){
        return competition.getDistance()/pigeonDistance;
    }
    public double calcSpeedAverage(List<Result> results){
        double totalSpeed = results.stream().mapToDouble(result-> result.getSpeed()).sum();
        return totalSpeed / results.size();
    }
    public double calcPoints(double averageSpeed , double speed){
        return (speed / averageSpeed)*100;
    }
}
