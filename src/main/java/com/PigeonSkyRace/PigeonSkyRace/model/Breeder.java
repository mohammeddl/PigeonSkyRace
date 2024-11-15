package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.Data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document("breeders")
public class Breeder {
    @Id
    private ObjectId id;
    private String name;
    private String password;
    private String email;
    private String doveCote;
    private String gpsCoordinates;
    @Field("pigeons")
    private List<String> pigeonIds;

    public Breeder(String name, String email, String gpsCoordinates) {
        this.name = name;
        this.email = email;
        this.gpsCoordinates = gpsCoordinates;
    }
    public Breeder(){}
}
