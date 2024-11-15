package com.PigeonSkyRace.PigeonSkyRace.dto;

import com.PigeonSkyRace.PigeonSkyRace.model.Competition;
import com.PigeonSkyRace.PigeonSkyRace.model.Pigeon;

public record RankingDTO(Competition competition , String breederName , String doveCote , Pigeon pigeon , double speed){
}
