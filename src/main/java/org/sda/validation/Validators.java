package org.sda.validation;

import static java.lang.Character.isUpperCase;

public class Validators {

    public boolean validatePassword(String password) {
        if (!isWithinRange(password, 3, 10)) {
            return false;
        }
        int upperCaseCount = 0;
        for (int a = 0; a < password.length(); a++) {
            char c = password.charAt(a);
            if (isUpperCase(c)) {
                upperCaseCount++;
            }
        }
        if (upperCaseCount >= 2) {
            return true;
        }
        return false;
    }

    private boolean isWithinRange(String password, int minimum, int maximum) {
        if (password.length() >= minimum && password.length() <= maximum) {
            return true;
        }
        return false;
    }
}
