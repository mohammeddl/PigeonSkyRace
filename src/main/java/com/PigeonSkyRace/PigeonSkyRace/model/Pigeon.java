package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pigeons")
public class Pigeon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ringNumber;
    private LocalDate birthDate;
    private String sex;
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
