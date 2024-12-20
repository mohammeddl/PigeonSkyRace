package com.PigeonSkyRace.PigeonSkyRace.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.CompetitionDto;
import com.PigeonSkyRace.PigeonSkyRace.model.*;
import com.PigeonSkyRace.PigeonSkyRace.repository.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ORGANIZER')")
    public Competition createCompetition(CompetitionDto competitionDto, String organizerEmail) {
        User organizer = userRepository.findByEmail(organizerEmail)
                .orElseThrow(() -> new RuntimeException("Organizer not found"));

        Competition competition = new Competition();
        competition.setName(competitionDto.raceName());
        
        return competitionRepository.save(competition);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public void closeCompetition(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));
        
    }

    @PreAuthorize("hasRole('USER')")
    public void registerForCompetition(Long competitionId, String userEmail) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        competition.getParticipants().add(user);
        competitionRepository.save(competition);
    }

    
}