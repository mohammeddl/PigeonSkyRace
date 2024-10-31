package com.PigeonSkyRace.PigeonSkyRace.dto;

import java.util.List;

import com.PigeonSkyRace.PigeonSkyRace.model.Pigeon;



public record UserRegistrationDto(String name, String password, String email, String doveCote, String gpsCoordinates, List<Pigeon> pigeons) {
   
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDoveCote() {
        return doveCote;
    }

    public String getGpsCoordinates() {
        return gpsCoordinates;
    }

    public List<Pigeon> getPigeons() {
        return pigeons;
    }

} 