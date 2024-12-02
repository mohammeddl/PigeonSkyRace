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

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoveCote() {
        return doveCote;
    }

    public void setDoveCote(String doveCote) {
        this.doveCote = doveCote;
    }

    public String getGpsCoordinates() {
        return gpsCoordinates;
    }

    public void setGpsCoordinates(String gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }

    public List<String> getPigeonIds() {
        return pigeonIds;
    }

    public void setPigeonIds(List<String> pigeonIds) {
        this.pigeonIds = pigeonIds;
    }

    private String doveCote;
    private String gpsCoordinates;
    @Field("pigeons")
    private List<String> pigeonIds;

    public Breeder(String name, String password, String email, String gpsCoordinates) {
        this.name = name;
        this.email = email;
        this.gpsCoordinates = gpsCoordinates;
    }

    public Breeder() {
    }
}
