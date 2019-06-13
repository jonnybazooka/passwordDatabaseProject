package org.sda.authentication.authService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sda.authentication.hashFunction.HashFunction;
import org.sda.user.User;

public class AuthServiceImplementation implements AuthService {

    private static final Logger LOGGER = LogManager.getLogger(AuthServiceImplementation.class.getName());

    @Override
    public boolean isAuthenticated(User user, HashFunction hashFunction, String password) {
        String hashedPassword = hashFunction.hash(password);
        if (user != null) {
            LOGGER.debug("User authentication started.");
            return user.getPassword().equalsIgnoreCase(hashedPassword);
        }
        LOGGER.debug("User not authenticated.");
        return false;
    }
}
