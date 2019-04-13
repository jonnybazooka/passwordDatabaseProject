package validators;

import org.junit.Test;
import org.sda.validation.Validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserNameValidationTest {

    private Validators validators = new Validators();

    @Test
    public void nameValidator1() {
        String name = "Michael";
        boolean validated = validators.validateUserName(name);
        assertTrue("Expected true, valid name", validated);
    }

    @Test
    public void nameValidator2() {
        String name = "Bill";
        boolean validated = validators.validateUserName(name);
        assertFalse("Expected false, username too short", validated);
    }

    @Test
    public void nameValidator3() {
        String name = "3Michael";
        boolean validated = validators.validateUserName(name);
        assertFalse("Expected false, username starts with a digit", validated);
    }

    @Test
    public void nameValidator4() {
        String name = "BillMichael4RomeoJulietBaronSascha";
        boolean validated = validators.validateUserName(name);
        assertFalse("Expected false, username too long", validated);
    }
}
