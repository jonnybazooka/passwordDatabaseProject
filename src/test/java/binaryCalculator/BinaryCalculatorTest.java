package binaryCalculator;

import org.junit.Test;
import org.sda.authentication.BinaryCalculator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BinaryCalculatorTest {
    @Test
    public void shouldReturn1111100form124() {
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(1);
        expected.add(1);
        expected.add(1);
        expected.add(1);
        expected.add(0);
        expected.add(0);
        List<Integer> calculated = BinaryCalculator.decimalToBinaryConverter(124);
        assertEquals(expected, calculated);
    }

    @Test
    public void shouldReturn1010form10() {
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(0);
        expected.add(1);
        expected.add(0);
        List<Integer> calculated = BinaryCalculator.decimalToBinaryConverter(10);
        assertEquals(expected, calculated);
    }
}
