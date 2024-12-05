package com.PigeonSkyRace.PigeonSkyRace.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "competitions")
@Data
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @ManyToMany
    private List<User> participants;
}