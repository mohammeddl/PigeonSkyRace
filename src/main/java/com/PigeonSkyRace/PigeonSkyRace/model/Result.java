package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("results")
public class Result {
    @Id
    private int id;
    private String pigeon;
    private double distance;
    private double flightTime;
    private double speed;
    private double adjustmentCoefficient;
    private double points;
}
