package com.PigeonSkyRace.PigeonSkyRace.helper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class Validator {
    final String emailRegex = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$";

    public boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (email.matches(emailRegex)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    public boolean validateString(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }else{
        return true;
        }
    }
    public boolean validateDouble(Double foo) {
        if (foo == null || foo < 0) {
            return false;
        }else{
            return true;
        }
    }
    public boolean validateDepartureDate(LocalDate departureDate) {
        if (departureDate.equals(LocalDate.of(2024, 12, 12))) {
            return true;
        }
        return false;
    }
}
