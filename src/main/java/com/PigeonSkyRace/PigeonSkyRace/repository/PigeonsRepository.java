package com.PigeonSkyRace.PigeonSkyRace.repository;

import com.PigeonSkyRace.PigeonSkyRace.model.Pigeon;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PigeonsRepository extends MongoRepository<Pigeon, String> {
}
