package org.sda.encryption;

public interface Encoder {
    char[] encrypt (char[] text);
    char[] decrypt (char[] text);
}
