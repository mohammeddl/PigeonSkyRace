package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Document("pigeons")
public class Pigeon {
    @Id
    private String ringNumber;
    private String sex;
    private int age;
    private String color;
    private String breeder;
}
