package org.sda.authentication;

import java.util.ArrayList;
import java.util.List;

public class BinaryCalculator {

    public static List<Integer> decimalToBinaryConverter(int decimal) {
        int current = decimal;
        List<Integer> binaryTable = new ArrayList<>();
        while (current > 0) {
            int mod = current % 2;
            current = current / 2;
            binaryTable.add(mod);
        }
        List<Integer> reversedTable = new ArrayList<>();
        for (int i = 0; i < binaryTable.size(); i++){
            reversedTable.add(binaryTable.get(binaryTable.size()-1-i));
        }
        return reversedTable;
    }
}
