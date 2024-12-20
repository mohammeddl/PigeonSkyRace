package com.PigeonSkyRace.PigeonSkyRace.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email; 
    
    private String doveCote;
    private String gpsCoordinates;

    @OneToMany(mappedBy = "user")
    private List<Pigeon> pigeons;
}