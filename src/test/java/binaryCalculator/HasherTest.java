package binaryCalculator;

import org.junit.Test;
import org.sda.authentication.Hasher;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HasherTest {

    @Test
    public void hasherTest1() {
        List<Character> result = Hasher.hasher("Very Strong Password");
        List<Character> expected = new ArrayList<>();
        expected.add('5');
        expected.add('6');
        expected.add('6');
        expected.add('5');
        expected.add('7');
        expected.add('2');
        expected.add('7');
        expected.add('9');
        expected.add('2');
        expected.add('7');
        expected.add('5');
        expected.add('3');
        expected.add('7');
        expected.add('4');
        expected.add('7');
        expected.add('2');
        expected.add('6');
        expected.add('1');
        expected.add('7');
        expected.add('6');
        expected.add('1');
        expected.add('6');
        expected.add('6');
        expected.add('7');
        expected.add('2');
        expected.add('7');
        expected.add('5');
        expected.add('7');
        expected.add('6');
        expected.add('1');
        expected.add('7');
        expected.add('3');
        expected.add('7');
        expected.add('3');
        expected.add('7');
        expected.add('7');
        expected.add('6');
        expected.add('1');
        expected.add('7');
        expected.add('7');
        expected.add('2');
        expected.add('6');
        expected.add('4');
        assertEquals(expected, result);
    }
}
