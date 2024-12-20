package com.PigeonSkyRace.PigeonSkyRace.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PigeonSkyRace.PigeonSkyRace.Mapper.PigeonMapper;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.PigeonsRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.PigeonsResponse;
import com.PigeonSkyRace.PigeonSkyRace.model.User;
import com.PigeonSkyRace.PigeonSkyRace.repository.PigeonsRepository;
import com.PigeonSkyRace.PigeonSkyRace.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PigeonService {

    private final PigeonsRepository pigeonsRepository;
    private final PigeonMapper pigeonMapper;
    private final UserRepository userRepository;

    @PreAuthorize("hasRole('USER')")
    public PigeonsResponse registerPigeon(PigeonsRequest pigeonRequest, String loggedInUserEmail) {
        User user = userRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var pigeon = pigeonMapper.pigeonRequestToPigeonEntity(pigeonRequest);
        pigeon.setUser(user);
        var savedPigeon = pigeonsRepository.save(pigeon);
        return pigeonMapper.pigeonEntityToPigeonResponse(savedPigeon);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public Page<PigeonsResponse> getAllPigeons(Pageable pageable) {
        return pigeonsRepository.findAll(pageable)
                .map(pigeonMapper::pigeonEntityToPigeonResponse);
    }

    @PreAuthorize("hasRole('USER') and @securityService.isOwner(#userEmail)")
    public Page<PigeonsResponse> getUserPigeons(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return pigeonsRepository.findByUser(user, pageable)
                .map(pigeonMapper::pigeonEntityToPigeonResponse);
    }
}