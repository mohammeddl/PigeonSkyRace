package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document("rankings")
public class Ranking {
    private String competition;
    private String breederName;
    private String doveCote;
    private Pigeon pigeon;
    private double speed;
}
