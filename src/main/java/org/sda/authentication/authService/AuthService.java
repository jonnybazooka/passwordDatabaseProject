package org.sda.authentication.authService;

import org.sda.authentication.hashFunction.HashFunction;
import org.sda.user.User;

public interface AuthService {
    boolean isAuthenticated (User user, HashFunction hashFunction, String password);
}
