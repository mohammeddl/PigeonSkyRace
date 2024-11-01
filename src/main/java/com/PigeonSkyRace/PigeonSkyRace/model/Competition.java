package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.Data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Document("competitions")
public class Competition {
    @Id
    private ObjectId id;
    private String raceName;
    private String releasePointGps;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private double distance;
    @Field("breeders")
    private List<String> breeders;
}
