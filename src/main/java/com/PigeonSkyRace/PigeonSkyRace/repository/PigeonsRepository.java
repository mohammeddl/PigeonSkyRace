package com.PigeonSkyRace.PigeonSkyRace.repository;

import com.PigeonSkyRace.PigeonSkyRace.model.Pigeon;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PigeonsRepository extends JpaRepository<Pigeon, Long> {
Page<Pigeon> findAll(Pageable pageable);
}
