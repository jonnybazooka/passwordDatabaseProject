package org.sda.encryption;

public class CustomCypher implements Encoder {

    private String scrambler;

    public CustomCypher(String scrambler) {
        this.scrambler = scrambler;
    }

    public char[] encrypt(char[] text) {
        char[] scramblerArray = scrambler.toCharArray();
        char[] output = new char[text.length];
        int scramblerIndex = 0;
        for (int i = 0; i < text.length; i++) {
            if (i % 2 == 0) {
                output[i] = (char)(text[i] + scramblerArray[scramblerIndex++]);
            } else {
                output[i] = (char)(text[i] - scramblerArray[scramblerIndex++]);
            }
            if (scramblerIndex == scramblerArray.length) {
                scramblerIndex = 0;
            }
        }
        return output;
    }

    public char[] decrypt(char[] text) {
        char[] scramblerArray = scrambler.toCharArray();
        char[] output = new char[text.length];
        int scramblerIndex = 0;
        for (int i = 0; i < text.length; i++) {
            if (i % 2 == 0) {
                output[i] = (char)(text[i] - scramblerArray[scramblerIndex++]);
            } else {
                output[i] = (char)(text[i] + scramblerArray[scramblerIndex++]);
            }
            if (scramblerIndex == scramblerArray.length) {
                scramblerIndex = 0;
            }
        }
        return output;
    }
}
