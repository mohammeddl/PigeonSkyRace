package com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions;

public class NoUserWasFoundException extends RuntimeException {
    private String message;
    public NoUserWasFoundException() {
    }
    public NoUserWasFoundException(String message) {
        super(message);
        this.message = message;
    }
}
