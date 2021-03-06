package validators;

import org.junit.Test;
import org.sda.validation.Validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailValidationTest {

    @Test
    public void emailValidator1() {
        Validators validators = new Validators();
        String email = "tgieraltowski@gmail.com";
        boolean validated = validators.validateEmail(email);
        assertTrue("Expected true, standard email", validated);
    }

    @Test
    public void emailValidator2() {
        Validators validators = new Validators();
        String email = "t_gieraltowski@gmail.com";
        boolean validated = validators.validateEmail(email);
        assertFalse("Expected false, illegal character", validated);
    }

    @Test
    public void emailValidator3() {
        Validators validators = new Validators();
        String email = "tg@gmail.com";
        boolean validated = validators.validateEmail(email);
        assertFalse("Expected false, @ in wrong position", validated);
    }

    @Test
    public void emailValidator4() {
        Validators validators = new Validators();
        String email = "tgieraltowski@pl";
        boolean validated = validators.validateEmail(email);
        assertFalse("Expected false, @ in wrong position", validated);
    }

    @Test
    public void emailValidator5() {
        Validators validators = new Validators();
        String email = "tgie@raltowski@gmail.com";
        boolean validated = validators.validateEmail(email);
        assertFalse("Expected false, two @ characters", validated);
    }

    @Test
    public void emailValidator6() {
        Validators validators = new Validators();
        String email = "tgie..owski@gmail.com";
        boolean validated = validators.validateEmail(email);
        assertFalse("Expected false, two special characters in a row", validated);
    }
}
