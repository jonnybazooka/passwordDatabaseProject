package hashFunctionTest;

import org.junit.Test;
import org.sda.authentication.hashFunction.HashFunction;
import org.sda.authentication.hashFunction.SHA256;

import static org.junit.Assert.assertEquals;

public class sha256Tests {
    @Test
    public void sha256Test1() {
        HashFunction hashFunction = new SHA256();
        String result = hashFunction.hash("Very Strong Password".toCharArray());
        String expected = "2739c77e7c8bde2251179784b74dde2e08ae633d8b6485b1879dd759cc3c851a";
        assertEquals(expected, result);
    }
}
