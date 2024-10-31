package com.PigeonSkyRace.PigeonSkyRace.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PigeonSkyRace.PigeonSkyRace.model.Competition;

public interface CompetitionRepository extends MongoRepository<Competition, String> {}