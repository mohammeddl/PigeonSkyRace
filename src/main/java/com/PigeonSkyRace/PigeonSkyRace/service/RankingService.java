package com.PigeonSkyRace.PigeonSkyRace.service;

import com.PigeonSkyRace.PigeonSkyRace.Mapper.RankingMapper;
import com.PigeonSkyRace.PigeonSkyRace.dto.RankingDTO;
import com.PigeonSkyRace.PigeonSkyRace.model.*;
import com.PigeonSkyRace.PigeonSkyRace.repository.BreederRepository;
import com.PigeonSkyRace.PigeonSkyRace.repository.PigeonsRepository;
import com.PigeonSkyRace.PigeonSkyRace.repository.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class RankingService {
    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private BreederRepository breederRepository;
    @Autowired
    private PigeonsRepository pigeonsRepository;
    @Autowired
    private RankingMapper rankingMapper;
    public void saveRankings(Competition competition , List<Result> results){
        results.stream().forEach(result -> {
            Breeder breeder = breederRepository.findByPigeonId(result.getPigeon()).orElseThrow();
            Pigeon pigeon = pigeonsRepository.findById(result.getPigeon()).orElseThrow();
            Ranking ranking = new Ranking();
            ranking.setBreederName(breeder.getName());
            ranking.setCompetition(competition);
            ranking.setDoveCote(breeder.getDoveCote());
            ranking.setSpeed(result.getSpeed());
            ranking.setPigeon(pigeon);
            rankingRepository.save(ranking);
        });
    }
    public List<RankingDTO> getRankings(String competitionId){
        List<Ranking> rankings = rankingRepository.findAllRankingsByCompetitionId(competitionId);
        List<RankingDTO> rankingDTOs = new ArrayList<>();
        rankings.forEach(ranking -> {
            rankingDTOs.add(rankingMapper.RankingToRankingDTO(ranking));
        });
        return rankingDTOs;
    }
}
