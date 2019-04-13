package org.sda.authentication;

import java.util.ArrayList;
import java.util.List;

public class Hasher {

    public static List<Character> hasher(String string) {
        List<Character> finalList = new ArrayList<>();
        List<Character> currentList = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            List<Character> tempList = HexCalculator.anotherDecimalToHexConverter((int)string.charAt(i));
            currentList.addAll(tempList);
        }
        for (Character character : currentList) {
            if (character >= 'A' && character <= 'F') {
                finalList.addAll(octalConverter(character));
            } else {
                finalList.add(character);
            }
        }
        return finalList;
    }

    private static List<Character> octalConverter(Character character) {
        List<Character> result = new ArrayList<>();
        result.add('1');
        if (character == 'A') {
            result.add('2');
        } else if (character == 'B') {
            result.add('3');
        } else if (character == 'C') {
            result.add('4');
        } else if (character == 'D') {
            result.add('5');
        } else if (character == 'E') {
            result.add('6');
        } else if (character == 'F') {
            result.add('7');
        }
        return result;
    }
}
