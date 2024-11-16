package com.PigeonSkyRace.PigeonSkyRace.controller;

import com.PigeonSkyRace.PigeonSkyRace.dto.RankingDTO;
import com.PigeonSkyRace.PigeonSkyRace.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rankings")
public class RankingController {
    @Autowired
    private RankingService rankingService;

    @GetMapping("/get")
    public List<RankingDTO> getRankings(@RequestParam String competitionID) {
        return rankingService.getRankings(competitionID);
    }
}
