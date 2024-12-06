package com.PigeonSkyRace.PigeonSkyRace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PigeonSkyRace.PigeonSkyRace.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByGpsCoordinates(String existsByGpsCoordinates);
    
}
