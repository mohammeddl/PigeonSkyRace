package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("results")
public class Result {
    @DBRef
    private String pigeon;
    private double distance;
    private Duration flightTime;
    private double speed;
    private double adjustmentCoefficient;
    private double points;


    public Result(){

    }
    public Result(String pigeon, double distance, double v, double speed, double v1, double points) {


    public String getPigeon() {
        return pigeon;
    }

    public void setPigeon(String pigeon) {
        this.pigeon = pigeon;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Duration getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Duration flightTime) {
        this.flightTime = flightTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAdjustmentCoefficient() {
        return adjustmentCoefficient;
    }

    public void setAdjustmentCoefficient(double adjustmentCoefficient) {
        this.adjustmentCoefficient = adjustmentCoefficient;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;

    }

    public String getPigeon() {
        return pigeon;
    }

    public void setPigeon(String pigeon) {
        this.pigeon = pigeon;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Duration getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Duration flightTime) {
        this.flightTime = flightTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAdjustmentCoefficient() {
        return adjustmentCoefficient;
    }

    public void setAdjustmentCoefficient(double adjustmentCoefficient) {
        this.adjustmentCoefficient = adjustmentCoefficient;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
