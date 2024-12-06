package com.PigeonSkyRace.PigeonSkyRace.service;

import com.PigeonSkyRace.PigeonSkyRace.Mapper.PigeonMapper;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.PigeonsRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.PigeonsResponse;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NoUserWasFoundException;
import com.PigeonSkyRace.PigeonSkyRace.helper.Validator;
import com.PigeonSkyRace.PigeonSkyRace.model.Pigeon;
import com.PigeonSkyRace.PigeonSkyRace.model.PigeonResults;
import com.PigeonSkyRace.PigeonSkyRace.repository.PigeonsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PigeonsRepository pigeonsRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private UserService userService;
    @InjectMocks
    private PigeonService pigeonService;

    @Mock
    private PigeonMapper pigeonMapper;
    private PigeonsRequest pigeon;

    @BeforeEach
    void setUp() {
        pigeon = new PigeonsRequest("male", LocalDate.now(), "white");

    }

    @Test
    void testCreatePigeon() {
        when(pigeonMapper.pigeonRequestToPigeonEntity(pigeon)).thenReturn(new Pigeon());
        when(pigeonsRepository.save(any(Pigeon.class))).thenReturn(new Pigeon());
        PigeonsResponse pigeonResponse = pigeonService.registerPigeon(this.pigeon, "daali12@gamil.com");
        assertNotNull(pigeonResponse);
        verify(pigeonsRepository, times(1)).save(any(Pigeon.class));
    }

    
}
