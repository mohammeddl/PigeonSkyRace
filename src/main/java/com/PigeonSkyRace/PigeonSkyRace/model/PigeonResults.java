package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PigeonResults {
    @DBRef
    private String pigeon ;
    private LocalTime arrivalTime ;
    private String competitionID;

    public PigeonResults(String competitionID, String pigeon, LocalTime arrivalTime) {
        this.pigeon = pigeon;
        this.arrivalTime = arrivalTime;
        this.competitionID = competitionID;
    }
}
