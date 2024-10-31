package com.PigeonSkyRace.PigeonSkyRace.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PigeonSkyRace.PigeonSkyRace.model.Ranking;

public interface RankingRepository extends MongoRepository<Ranking, String> {}
