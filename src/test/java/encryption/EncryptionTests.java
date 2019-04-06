package encryption;

import org.junit.Test;
import org.sda.encryption.*;

import static org.junit.Assert.assertEquals;

public class EncryptionTests {
    @Test
    public void rot13TestEncryption() {
        char[] text = "redFoxJumps234".toCharArray();
        Encoder encoder = new ROT13();
        char[] output = encoder.encrypt(text);

        assertEquals("Text doesn't match expected result.", "erqSbkWhzcf234", new String(output));
    }

    @Test
    public void rot18TestEncryption() {
        char[] text = "redFoxJumps234".toCharArray();
        Encoder encoder = new ROT18();
        char[] output = encoder.encrypt(text);

        assertEquals("Text doesn't match expected result.", "erqSbkWhzcf789", new String(output));
    }

    @Test
    public void rot13DecryptionTest() {
        char[] text = "erqSbkWhzcf234".toCharArray();
        Encoder encoder = new ROT13();
        char[] output = encoder.decrypt(text);

        assertEquals("Text doesn't match expected result.", "redFoxJumps234", new String(output));
    }

    @Test
    public void rot18DecryptionTest() {
        char[] text = "erqSbkWhzcf789".toCharArray();
        Encoder encoder = new ROT18();
        char[] output = encoder.decrypt(text);

        assertEquals("Text doesn't match expected result.", "redFoxJumps234", new String(output));
    }

    @Test
    public void doubleEncryptionTest() {
        char[] text = "redFoxJumps234".toCharArray();
        Encoder encoder = new ROT13();
        char[] output = encoder.encrypt(text);
        output = encoder.encrypt(output);

        assertEquals("Text doesn't match expected result.", "redFoxJumps234", new String(output));
    }

    @Test
    public void specialCharactersTest() {
        char[] text = "redFoxJumps</$234".toCharArray();
        Encoder encoder = new ROT13();
        char[] output = encoder.encrypt(text);

        assertEquals("Text doesn't match expected result.", "erqSbkWhzcf</$234", new String(output));
    }

    @Test
    public void rot47EncryptionTest() {
        char[] text = "redFoxJumps234".toCharArray();
        Encoder encoder = new ROT47();
        char[] output = encoder.encrypt(text);

        assertEquals("Text doesn't match expected result.", "C65u@IyF>ADabc", new String(output));
    }

    @Test
    public void rot47Decryption() {
        char[] text = "C65u@IyF>ADabcoo".toCharArray();
        Encoder encoder = new ROT47();
        char[] output = encoder.encrypt(text);

        assertEquals("Text doesn't match expected result.", "redFoxJumps234@@", new String(output));
    }

    @Test
    public void customCypherEncryptionTest() {
        char[] text = "Red fox jumpe a lot".toCharArray();
        Encoder encoder = new CustomCypher("passphrase");
        char[] output = encoder.encrypt(text);

        assertEquals("Text doesn't match expected result.", "Â×ﾭÖê﾿ÝÝØﾭÑﾸÞç", new String(output));
    }

    @Test
    public void customCypherDecryptionTest() {
        char[] text = "Â×ﾭÖê﾿ÝÝØﾭÑﾸÞç".toCharArray();
        Encoder encoder = new CustomCypher("passphrase");
        char[] output = encoder.decrypt(text);

        assertEquals("Text doesn't match expected result.", "Red fox jumpe a lot", new String(output));
    }
}
