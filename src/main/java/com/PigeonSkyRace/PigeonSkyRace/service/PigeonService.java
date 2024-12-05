package com.PigeonSkyRace.PigeonSkyRace.service;

import com.PigeonSkyRace.PigeonSkyRace.Mapper.PigeonMapper;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.PigeonsRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.PigeonsResponse;
import com.PigeonSkyRace.PigeonSkyRace.model.User;
import com.PigeonSkyRace.PigeonSkyRace.repository.PigeonsRepository;
import com.PigeonSkyRace.PigeonSkyRace.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PigeonService {

    private final PigeonsRepository pigeonsRepository;
    private final PigeonMapper pigeonMapper;
    private final UserRepository userRepository;

    public PigeonsResponse registerPigeon(PigeonsRequest pigeonRequest, String loggedInUserEmail) {
        User user = userRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var pigeon = pigeonMapper.pigeonRequestToPigeonEntity(pigeonRequest);
        pigeon.setUser(user);
        var savedPigeon = pigeonsRepository.save(pigeon);
        return pigeonMapper.pigeonEntityToPigeonResponse(savedPigeon);
    }

    public Page<PigeonsResponse> getAllPigeons(Pageable pageable) {
        return pigeonsRepository.findAll(pageable).map(pigeonMapper::pigeonEntityToPigeonResponse);
    }
}
