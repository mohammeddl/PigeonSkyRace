package com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions;

public class CompetitionNotFinishedException extends RuntimeException {
    private String message;
    public CompetitionNotFinishedException() {
    }
    public CompetitionNotFinishedException(String message) {
        super(message);
    }
}
