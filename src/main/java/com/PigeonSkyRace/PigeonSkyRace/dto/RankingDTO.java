package com.PigeonSkyRace.PigeonSkyRace.dto;

import com.PigeonSkyRace.PigeonSkyRace.model.Breeder;
import com.PigeonSkyRace.PigeonSkyRace.model.Competition;
import com.PigeonSkyRace.PigeonSkyRace.model.Result;

public record RankingDTO(Breeder breeder , Result result , Competition competition){
}
