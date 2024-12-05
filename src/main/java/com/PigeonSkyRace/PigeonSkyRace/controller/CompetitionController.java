package com.PigeonSkyRace.PigeonSkyRace.controller;

import com.PigeonSkyRace.PigeonSkyRace.dto.GpsPointDto;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.CompetitionDto;
import com.PigeonSkyRace.PigeonSkyRace.model.PigeonResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.PigeonSkyRace.PigeonSkyRace.model.Competition;
import com.PigeonSkyRace.PigeonSkyRace.service.CompetitionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Competition API", description = "Operations related to competitions")
@RestController
@RequestMapping("/api/competitions")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    // @Operation(summary   = "Create a new competition", description = "Provide competition details to create a new competition")
    // @PostMapping("/create")
    // public Competition createCompetition(@RequestBody CompetitionDto competitionDto) {
    //     return competitionService.createCompetition(competitionDto);
    // }

    // @Operation(summary = "Close a competition", description = "Close a competition and provide results")
    // @PostMapping("/close")
    // public List<Result> closeCompetition(@RequestBody List<PigeonResults> results) {
    //     return competitionService.closeCompetition(results);
    // }

    // @Operation(summary = "Calculate distance", description = "Calculate the distance between release and arrival points")
    // @PostMapping("/distance")
    // public double distance(@RequestBody GpsPointDto gpsPointDto) {
    //     return competitionService.calcDistance(gpsPointDto.releasePoint(), gpsPointDto.arrivalPoint());
    // }

}
