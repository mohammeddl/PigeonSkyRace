package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("pigeons")
public class Pigeon {
    @Id
    private String ringNumber;
    private String sex;
    private int age;
    private String color;
}
