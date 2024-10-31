package com.PigeonSkyRace.PigeonSkyRace.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PigeonSkyRace.PigeonSkyRace.model.Breeder;

public interface BreederRepository extends MongoRepository<Breeder, String> {
    boolean existsByEmail(String email);

}