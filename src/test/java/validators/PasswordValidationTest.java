/*
package validators;

import org.junit.*;
import org.sda.validation.Validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordValidationTest {

    private Validators validators = new Validators();
    private static int count = 0;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before class. Opening remote connection.");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("After class. Closing remote connection.");
    }

    @Before
    public void count() {
        count++;
    }

    @After
    public void message() {
        System.out.println("Test completed.");
    }

    @Test
    public void passwordValidator1() {
        System.out.println("Test no.: " + count);
        String password = "ABcdEF66,/@";
        boolean validated = validators.validatePassword(password);
        assertTrue("Expected true, valid password", validated);
    }

    @Test
    public void passwordValidator2() {
        System.out.println("Test no.: " + count);
        String password = "ABcdEF66,/@*";
        boolean validated = validators.validatePassword(password);
        assertFalse("Expected false, illegal characters", validated);
    }

    @Test
    public void passwordValidator3() {
        System.out.println("Test no.: " + count);
        String password = "ABcdEF66,/@NNVn449,,ADmm78";
        boolean validated = validators.validatePassword(password);
        assertFalse("Expected false, password too long", validated);
    }
}
*/
