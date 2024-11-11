package com.PigeonSkyRace.PigeonSkyRace.controller;

import com.PigeonSkyRace.PigeonSkyRace.dto.PigeonsResultsDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.ResultDto;
import com.PigeonSkyRace.PigeonSkyRace.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PigeonSkyRace.PigeonSkyRace.dto.CompetitionDto;
import com.PigeonSkyRace.PigeonSkyRace.model.Competition;
import com.PigeonSkyRace.PigeonSkyRace.service.CompetitionService;

import java.util.List;

@RestController
@RequestMapping("/api/competitions")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @PostMapping("/create")
    public Competition createCompetition(@RequestBody CompetitionDto competitionDto) {
        return competitionService.createCompetition(competitionDto);
    }

    @PostMapping("/close")
    public List<Result> closeCompetition(@RequestBody List<PigeonsResultsDto> resultDtos , String competitionID) {
        return competitionService.closeCompetition(resultDtos,competitionID);
    }
    
}
