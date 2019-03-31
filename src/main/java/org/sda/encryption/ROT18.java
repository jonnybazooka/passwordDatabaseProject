package org.sda.encryption;

public class ROT18 extends Rotators {

    private ROT13 encoder = new ROT13();

    public char rotateChar(char c) {
        if (Character.isLetter(c)) {
            return encoder.rotateChar(c);
        } else if (c >= '0' && c <= '4') {
            c += 5;
        } else if (c >= '5' && c <= '9') {
            c -= 5;
        }
        return c;
    }
}
