package com.PigeonSkyRace.PigeonSkyRace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PigeonSkyRace.PigeonSkyRace.dto.UserRegistrationDto;
import com.PigeonSkyRace.PigeonSkyRace.model.Breeder;
import com.PigeonSkyRace.PigeonSkyRace.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.server.ResponseStatusException;

@Api(value = "User API", description = "Operations related to user management")
@RequestMapping("/api/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "Register a new user", notes = "Register a new breeder with pigeons")
    @PostMapping("/register")
    public ResponseEntity<?> registerBreederWithPigeons(@RequestBody UserRegistrationDto registrationDTO) {
        if (userService.emailExists(registrationDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        if(userService.gpsCoordinatesEsists(registrationDTO.getGpsCoordinates())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("gps Coordinates already exists");
        }
        Breeder breeder = userService.registerBreederWithPigeons(registrationDTO);
        return ResponseEntity.ok(breeder);
    }

    @ApiOperation(value = "User login", notes = "Authenticate a user with email and password")
    @PostMapping("/login")
    public ResponseEntity<Breeder> login(@RequestBody UserRegistrationDto registrationDTO) {
        if (!userService.emailExists(registrationDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        Breeder breeder = userService.login(registrationDTO.email(), registrationDTO.password());
        return ResponseEntity.ok(breeder);
    }
}
