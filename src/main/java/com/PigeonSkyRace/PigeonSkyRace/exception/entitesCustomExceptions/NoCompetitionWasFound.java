package com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions;

public class NoCompetitionWasFound extends RuntimeException {
    private String message;
    public NoCompetitionWasFound() {}
    public NoCompetitionWasFound(String message) {
        super(message);
    }
}
