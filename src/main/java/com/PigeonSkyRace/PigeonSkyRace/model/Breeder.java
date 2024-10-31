package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document("breeders")
public class Breeder extends User{
    private String doveCote;
    private String gpsCoordinates;
    @Field("pigeons")
    private List<String> pigeons;
}
