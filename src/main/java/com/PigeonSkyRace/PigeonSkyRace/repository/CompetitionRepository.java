package com.PigeonSkyRace.PigeonSkyRace.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.PigeonSkyRace.PigeonSkyRace.model.Competition;

public interface CompetitionRepository extends JpaRepository<Competition, String> {}