package org.sda.validation;

import static java.lang.Character.*;

public class Validators {

    private char[] passwordLegalCharacters = {' ', ',', '@', '!', '/', '^'};
    private char[] emailLegalCharacters = {'.', ',', '@', '-'};

    public boolean validatePassword(String password) {
        if (!isWithinRange(password, 7, 15)) {
            return false;
        }
        int upperCaseCount = 0;
        int digitsCount = 0;
        for (int a = 0; a < password.length(); a++) {
            char c = password.charAt(a);
            if (isUpperCase(c)) {
                upperCaseCount++;
            }
            if (isDigit(c)) {
                digitsCount++;
            }
        }
        if (upperCaseCount >= 2 && digitsCount >= 2 && containsValidCharacterSet(password, passwordLegalCharacters)) {
            return true;
        }
        return false;
    }

    public boolean validateEmail(String email) {
        if (!isWithinRange(email, 7, 25)) {
            System.out.println("Email must be 7-25 characters long.");
            return false;
        }
        if (!containsValidCharacterSet(email, emailLegalCharacters)) {
            System.out.println("Email can contain only '.', ',', '@', '-' special characters.");
            return false;
        }
        if (!containsOneMonkey(email)) {
            System.out.println("Email must contain exactly one @.\nIt cannot be one of the first three or last three characters.");
            return false;
        }
        if (containsNeighbouringSpecialCharacters(email)) {
            System.out.println("Special characters cannot neighbour each other.");
            return false;
        }
        return true;
    }

    private boolean containsNeighbouringSpecialCharacters(String email) {
        boolean previousCharWasSpecial = false;
        for (int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);
            if (!isDigit(c) && !isLetter(c) && previousCharWasSpecial) {
                return true;
            } else if (isDigit(c) || isLetter(c)) {
                previousCharWasSpecial = false;
            } else {
                previousCharWasSpecial = true;
            }
        }
        return false;
    }

    private boolean containsOneMonkey(String email) {
        int monkeyCount = 0;
        int monkeyIndex = 0;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                monkeyCount++;
                monkeyIndex = i;
            }
        }
        if (monkeyIndex < 3 || monkeyIndex > (email.length()-4)) {
            return false;
        } else if (monkeyCount != 1) {
            return false;
        } else {
            return true;
        }
    }

    private boolean containsValidCharacterSet(String string, char[] legalCharacters) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if(Character.isLetter(c) || Character.isDigit(c)) {
                continue;
            } else {
                if (isAdditionalLegalCharacter(c, legalCharacters)) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAdditionalLegalCharacter(char c, char[] legalCharacters) {
        for (int i = 0; i < legalCharacters.length; i++) {
            if (legalCharacters[i] == c) {
                return true;
            }
        }
        return false;
    }

    private boolean isWithinRange(String password, int minimum, int maximum) {
        if (password.length() >= minimum && password.length() <= maximum) {
            return true;
        }
        return false;
    }

    private boolean isStartingWithANumber(String string) {
        if (Character.isDigit(string.charAt(0))) {
            return true;
        }
        return false;
    }

    public boolean doPasswordsMatch(String password1, String password2) {
        if (password1.equals(password2)) {
            return true;
        }
        return false;
    }

    public boolean validateUserName(String name) {
        if (!isWithinRange(name, 5, 15) || isStartingWithANumber(name)) {
            System.out.println("Name has to be 5-15 characters long and cannot start with a digit.");
            return false;
        }
        return true;
    }
}
