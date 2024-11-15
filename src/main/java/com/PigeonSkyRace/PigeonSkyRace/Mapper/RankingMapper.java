package com.PigeonSkyRace.PigeonSkyRace.Mapper;

import com.PigeonSkyRace.PigeonSkyRace.dto.RankingDTO;
import com.PigeonSkyRace.PigeonSkyRace.model.Competition;
import com.PigeonSkyRace.PigeonSkyRace.model.Ranking;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RankingMapper {
    Ranking RankingDtoToRanking(RankingDTO rankingDTO);
    @Named("competitionToId")
    default String mapCompetitionToId(Competition competition) {
        return competition != null ? competition.getId() : null;
    }
    RankingDTO RankingToRankingDTO(Ranking ranking);
}
