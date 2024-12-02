package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document("rankings")
public class Ranking {
    private Competition competition;
    private String breederName;
    private String doveCote;
    private Pigeon pigeon;
    private double speed;

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public String getBreederName() {
        return breederName;
    }

    public void setBreederName(String breederName) {
        this.breederName = breederName;
    }

    public String getDoveCote() {
        return doveCote;
    }

    public void setDoveCote(String doveCote) {
        this.doveCote = doveCote;
    }

    public Pigeon getPigeon() {
        return pigeon;
    }

    public void setPigeon(Pigeon pigeon) {
        this.pigeon = pigeon;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


}
