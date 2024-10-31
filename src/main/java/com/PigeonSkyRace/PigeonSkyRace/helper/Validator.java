package com.PigeonSkyRace.PigeonSkyRace.helper;

import org.springframework.stereotype.Component;

@Component
public class Validator {
    final String emailRegex = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$";
    public boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (email.matches(emailRegex)){
            return true;
        }else{
            return false;
        }
    }
    public boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }else{
            return true;
        }
    }
}
