package binaryCalculator;

import org.junit.Test;
import org.sda.authentication.SevenBasedCalculator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SevenBasedCalculatorTest {
    @Test
    public void shouldReturn20form14() {
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(0);
        List<Integer> calculated = SevenBasedCalculator.decimalToSevenBasedConverter(14);
        assertEquals(expected, calculated);
    }

    @Test
    public void shouldReturn25form19() {
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(5);
        List<Integer> calculated = SevenBasedCalculator.decimalToSevenBasedConverter(19);
        assertEquals(expected, calculated);
    }
}
