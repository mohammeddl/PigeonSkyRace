package com.PigeonSkyRace.PigeonSkyRace.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PigeonSkyRace.PigeonSkyRace.model.Result;

public interface ResultRepository extends MongoRepository<Result, String> {}