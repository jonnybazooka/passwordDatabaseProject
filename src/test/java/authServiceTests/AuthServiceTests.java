package authServiceTests;

import org.junit.Test;
import org.sda.authentication.authService.AuthService;
import org.sda.authentication.authService.AuthServiceImplementation;
import org.sda.authentication.hashFunction.SHA256;
import org.sda.user.User;

import static junit.framework.TestCase.assertTrue;

public class AuthServiceTests {
    @Test
    public void authServiceTest1() {
        User user = new User(null, "Victor");
        user.addPassword("Very Strong Password".toCharArray());
        AuthService authService = new AuthServiceImplementation();
        boolean result = authService.isAuthenticated(user, new SHA256());
        assertTrue(result);
    }
}
