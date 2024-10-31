package com.PigeonSkyRace.PigeonSkyRace.controller;

import com.PigeonSkyRace.PigeonSkyRace.model.Pigeon;
import com.PigeonSkyRace.PigeonSkyRace.repository.PigeonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PigeonController {

    @Autowired
    private PigeonsRepository pigeonsRepository ;
    @PostMapping("/api/addPigeon")
    public void addPigeon(@RequestBody Pigeon pigeon) {
        pigeonsRepository.insert(pigeon);
    }
}
