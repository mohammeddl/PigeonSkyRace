package com.PigeonSkyRace.PigeonSkyRace.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.PigeonSkyRace.PigeonSkyRace.dto.request.PigeonsRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.PigeonsResponse;
import com.PigeonSkyRace.PigeonSkyRace.service.PigeonService;

import org.springframework.security.core.Authentication;
// import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pigeons")
@RequiredArgsConstructor
public class PigeonController {

    private final PigeonService pigeonService;

    @PostMapping
    public ResponseEntity<PigeonsResponse> registerPigeon( @RequestBody @Valid  PigeonsRequest pigeonRequest, Authentication authentication) {
        String loggedInUserEmail = authentication.getName();    
        System.out.println("Logged-in user: " + loggedInUserEmail);
        System.out.println("User authorities: " + authentication.getAuthorities());
    
        PigeonsResponse pigeonResponse = pigeonService.registerPigeon(pigeonRequest,loggedInUserEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(pigeonResponse);
    }

    @GetMapping
    public ResponseEntity<Page<PigeonsResponse>> getAllPigeons(@RequestParam Pageable pageable) {
        return ResponseEntity.ok(pigeonService.getAllPigeons(pageable));
    }

}
