package com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions;

public class NegativeDurationException extends RuntimeException{
    public NegativeDurationException(){}
    public NegativeDurationException(String message){
        super(message);
    }
}
