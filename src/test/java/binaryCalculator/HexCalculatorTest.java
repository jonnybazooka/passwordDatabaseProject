package binaryCalculator;

import org.junit.Test;
import org.sda.authentication.HexCalculator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HexCalculatorTest {
    @Test
    public void shouldReturnFwhen15() {
        String result = HexCalculator.decimalToHexConverter(15);
        String expected = "F";
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturn1Fwhen31() {
        String result = HexCalculator.decimalToHexConverter(31);
        String expected = "1F";
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnC8when200() {
        String result = HexCalculator.decimalToHexConverter(200);
        String expected = "C8";
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturn4DCwhen1244() {
        String result = HexCalculator.decimalToHexConverter(1244);
        String expected = "4DC";
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturn4DCwhen1244secondMethod() {
        List<Character> result = HexCalculator.anotherDecimalToHexConverter(1244);
        List<Character> expected = new ArrayList<>();
        expected.add('4');
        expected.add('D');
        expected.add('C');
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnC8when200secondMethod() {
        List<Character> result = HexCalculator.anotherDecimalToHexConverter(200);
        List<Character> expected = new ArrayList<>();
        expected.add('C');
        expected.add('8');
        assertEquals(expected, result);
    }
}
