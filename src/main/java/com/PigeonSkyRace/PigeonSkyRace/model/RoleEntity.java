package com.PigeonSkyRace.PigeonSkyRace.model;

import com.PigeonSkyRace.PigeonSkyRace.enums.Role;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Role roleName;

}
