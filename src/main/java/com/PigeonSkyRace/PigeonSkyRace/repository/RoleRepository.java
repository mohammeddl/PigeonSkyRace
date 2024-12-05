package com.PigeonSkyRace.PigeonSkyRace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PigeonSkyRace.PigeonSkyRace.enums.Role;
import com.PigeonSkyRace.PigeonSkyRace.model.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(Role roleName);
    
}
