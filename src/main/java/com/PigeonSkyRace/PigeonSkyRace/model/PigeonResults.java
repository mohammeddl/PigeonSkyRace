package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PigeonResults {
    private String pigeon;
    private LocalTime arrivalTime;
    private String competitionID;

}
