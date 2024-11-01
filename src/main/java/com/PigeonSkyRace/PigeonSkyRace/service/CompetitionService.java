package com.PigeonSkyRace.PigeonSkyRace.service;

import java.util.List;
import java.util.stream.Collectors;

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
        return competitionRepository.save(competition);
    }

}
