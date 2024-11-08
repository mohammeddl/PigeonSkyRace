package com.PigeonSkyRace.PigeonSkyRace.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.PigeonSkyRace.PigeonSkyRace.dto.ResultDto;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.CompetitionNotFinishedException;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NoCompetitionWasFound;
import com.PigeonSkyRace.PigeonSkyRace.model.Result;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ResultDto> closeCompetition(List<ResultDto> ResultsDtos , String competitionID) {
        Competition competition = competitionRepository.findById(competitionID).orElseThrow(()->new NoCompetitionWasFound("with the following ID :"+competitionID));
        LocalTime competitionTime = (LocalTime) competition.getDuration().addTo(competition.getDepartureTime());
        if (ResultsDtos.stream().allMatch(resultDto ->
                validator.validateString(resultDto.pigeon())
                        &&validator.validateDouble(resultDto.distance())
                        &&validator.validateDouble(resultDto.flightTime())
                        &&validator.validateDouble(resultDto.points())
                        &&validator.validateDouble(resultDto.speed()))) {
            if (!competitionTime.equals(LocalTime.now())){
                throw new CompetitionNotFinishedException("Competition" + competition.getRaceName() + "have not finished yet");
            }
            List<Result> results = new ArrayList<>();
            ResultsDtos.forEach(resultDto -> {results.add(new Result(resultDto.pigeon(),resultDto.distance() , resultDto.flightTime(),
                    resultDto.speed() , resultDto.adjustmentCoefficient(), resultDto.points()));});

        }
    }
    public List<Double> calcPoints(List<ResultDto> resultDtos){
        double speedMean =
        return resultDtos.stream().
    }
}
