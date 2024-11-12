package com.PigeonSkyRace.PigeonSkyRace.service;

import com.PigeonSkyRace.PigeonSkyRace.repository.PigeonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PigeonService {
    @Autowired
    private PigeonsRepository pigeonsRepository;
}
