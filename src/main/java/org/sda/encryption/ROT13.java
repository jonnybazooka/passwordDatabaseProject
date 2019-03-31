package org.sda.encryption;

public class ROT13 extends Rotators {

    public char rotateChar(char c) {
            if (Character.isLetter(c)) {
                c = (char)(c + 13);
                c = verifyEncryptedCharacter(c);
            }
        return c;
    }

    private char verifyEncryptedCharacter(char ch) {
        if (ch <= 103 && ch >= 91) {
            ch -= 26;
        } else if (ch <= 136 && ch >= 123) {
            ch -= 26;
        }
        return ch;
    }
}
