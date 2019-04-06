package validators;

import org.junit.Test;
import org.sda.validation.Validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordValidationTest {

    private Validators validators = new Validators();

    @Test
    public void passwordValidator1() {
        String password = "ABcdEF66,/@";
        boolean validated = validators.validatePassword(password);
        assertTrue("Expected true, valid password", validated);
    }

    @Test
    public void passwordValidator2() {
        String password = "ABcdEF66,/@*";
        boolean validated = validators.validatePassword(password);
        assertFalse("Expected false, illegal characters", validated);
    }

    @Test
    public void passwordValidator3() {
        String password = "ABcdEF66,/@NNVn449,,ADmm78";
        boolean validated = validators.validatePassword(password);
        assertFalse("Expected false, password too long", validated);
    }
}
