package com.PigeonSkyRace.PigeonSkyRace.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import com.PigeonSkyRace.PigeonSkyRace.dto.request.PigeonsRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.PigeonsResponse;
import com.PigeonSkyRace.PigeonSkyRace.service.PigeonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/pigeons")
@RequiredArgsConstructor
@Tag(name = "Pigeons", description = "Operations related to pigeons")
@SecurityRequirement(name = "bearerAuth")
public class PigeonController {

    private final PigeonService pigeonService;

    @Operation(summary = "Register a new pigeon")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<PigeonsResponse> registerPigeon(
            @RequestBody @Valid PigeonsRequest pigeonRequest,
            @AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getClaim("email");
        log.info("Registering new pigeon for user: {}", userEmail);
        PigeonsResponse pigeonResponse = pigeonService.registerPigeon(pigeonRequest, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(pigeonResponse);
    }

    @Operation(summary = "Get all pigeons")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @GetMapping
    public ResponseEntity<Page<PigeonsResponse>> getAllPigeons(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable) {
        log.info("Getting all pigeons, requested by: {}", (Object) jwt.getClaim("email"));
        return ResponseEntity.ok(pigeonService.getAllPigeons(pageable));
    }

    @Operation(summary = "Get user's pigeons")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-pigeons")
    public ResponseEntity<Page<PigeonsResponse>> getMyPigeons(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable) {
        String userEmail = jwt.getClaim("email");
        log.info("Getting pigeons for user: {}", userEmail);
        return ResponseEntity.ok(pigeonService.getUserPigeons(userEmail, pageable));
    }
}