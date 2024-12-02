package com.PigeonSkyRace.PigeonSkyRace.controller;

import com.PigeonSkyRace.PigeonSkyRace.dto.RankingDTO;
import com.PigeonSkyRace.PigeonSkyRace.service.RankingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Ranking API", description = "Operations related to rankings")
@RestController
@RequestMapping("api/rankings")
public class RankingController {
    @Autowired
    private RankingService rankingService;
    @ApiOperation(value = "Get rankings", notes = "Retrieve rankings based on competition ID")
    @GetMapping("/get")
    public List<RankingDTO> getRankings(@RequestParam String competitionID) {
        return rankingService.getRankings(competitionID);
    }
}
