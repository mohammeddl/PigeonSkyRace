package com.PigeonSkyRace.PigeonSkyRace.repository;

import com.PigeonSkyRace.PigeonSkyRace.model.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.PigeonSkyRace.PigeonSkyRace.model.Ranking;

import java.util.List;

public interface RankingRepository extends MongoRepository<Ranking, String> {
    List<Ranking> findAllRankingsByCompetitionId(String competitionId);
}
