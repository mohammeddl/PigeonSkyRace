package com.PigeonSkyRace.PigeonSkyRace.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PigeonSkyRace.PigeonSkyRace.model.Breeder;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface BreederRepository extends MongoRepository<Breeder, String> {
    boolean existsByEmail(String email);
    boolean existsByGpsCoordinates(String existsByGpsCoordinates);
    Breeder findByEmail(String email);
    @Query("{ 'pigeonIds': ?0 }")
    Optional<Breeder> findByPigeonId(String pigeonId);
}