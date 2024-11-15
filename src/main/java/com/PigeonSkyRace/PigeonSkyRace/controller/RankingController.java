package com.PigeonSkyRace.PigeonSkyRace.controller;

import com.PigeonSkyRace.PigeonSkyRace.dto.RankingDTO;
import com.PigeonSkyRace.PigeonSkyRace.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/rankings")
public class RankingController {
    @Autowired
    private RankingService rankingService;

    public List<RankingDTO> getRankings(@RequestParam String competitionID) {

    }
}
