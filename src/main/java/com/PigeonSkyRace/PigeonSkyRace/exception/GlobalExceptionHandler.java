package com.PigeonSkyRace.PigeonSkyRace.exception;

import com.PigeonSkyRace.PigeonSkyRace.dto.response.ErrorDto;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.AccessDeniedException;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.CompetitionNotFinishedException;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NegativeDurationException;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NoCompetitionWasFound;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NoUserWasFoundException;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.UsernameAlreadyExistsException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(NoCompetitionWasFound.class)
    public ResponseEntity<String> handleCompetitionNotFoundException(NoCompetitionWasFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Competition not found" + ex.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        ErrorDto error = new ErrorDto("Username already exists", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDenied(AccessDeniedException ex) {
        ErrorDto error = new ErrorDto("Access Denied", "You do not have permission to access this resource.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(NegativeDurationException.class)
    public ResponseEntity<String> handleNegativeDurationException(NegativeDurationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
