package org.sda.encryption;

public class ROT47 extends Rotators {

    protected char rotateChar(char c) {
        if (c >= 33 && c <= 75) {
            c += 47;
        } else if (c >= 76 && c <= 126) {
            c -= 47;
        }
        return c;
    }
}
