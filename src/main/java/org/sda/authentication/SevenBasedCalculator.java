package org.sda.authentication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SevenBasedCalculator {

    public static List<Integer> decimalToSevenBasedConverter(int decimal) {
        int current = decimal;
        List<Integer> binaryTable = new ArrayList<>();
        while (current > 0) {
            int mod = current % 7;
            current = current / 7;
            binaryTable.add(mod);
        }
        Collections.reverse(binaryTable);
        return binaryTable;
    }
}
