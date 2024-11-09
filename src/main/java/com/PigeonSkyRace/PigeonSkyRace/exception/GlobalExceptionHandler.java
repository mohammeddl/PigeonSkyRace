package com.PigeonSkyRace.PigeonSkyRace.exception;

import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.CompetitionNotFinishedException;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NegativeDurationException;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NoCompetitionWasFound;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NoUserWasFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
    @ExceptionHandler(NoUserWasFoundException.class)
    public ResponseEntity<String> handleNoUserWasFoundException(NoUserWasFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(CompetitionNotFinishedException.class)
    public ResponseEntity<String> handleCompetitionNotFinishedException(CompetitionNotFinishedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " +ex.getMessage());
    }
    @ExceptionHandler(NoCompetitionWasFound.class)
    public ResponseEntity<String> handleCompetitionNotFoundException(NoCompetitionWasFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Competition not found" + ex.getMessage());
    }
    @ExceptionHandler(NegativeDurationException.class)
    public ResponseEntity<String> handleNegativeDurationException(NegativeDurationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
