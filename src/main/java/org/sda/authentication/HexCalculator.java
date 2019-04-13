package org.sda.authentication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HexCalculator {

    public static String decimalToHexConverter(int decimal) {
        int current = decimal;
        StringBuilder hexadecimalTable = new StringBuilder();
        while (current > 0) {
            int mod = current % 16;
            if (mod == 10) {
                hexadecimalTable.append("A");
            } else if (mod == 11){
                hexadecimalTable.append("B");
            } else if (mod == 12) {
                hexadecimalTable.append("C");
            } else if (mod == 13) {
                hexadecimalTable.append("D");
            } else if (mod == 14) {
                hexadecimalTable.append("E");
            } else if (mod == 15) {
                hexadecimalTable.append("F");
            } else {
                hexadecimalTable.append(mod);
            }
            current = current / 16;
        }
        hexadecimalTable.reverse();
        return hexadecimalTable.toString();
    }

    public static List<Character> anotherDecimalToHexConverter(int decimal) {
        int current = decimal;
        List<Character> hexadecimalTable = new ArrayList<>();
        while (current > 0) {
            int mod = current % 16;
            if (mod > 0 && mod < 10) {
                hexadecimalTable.add((char)(mod+48));
            } else {
                hexadecimalTable.add((char)(mod+55));
            }
            current = current / 16;
        }
        Collections.reverse(hexadecimalTable);
        return hexadecimalTable;
    }
}
