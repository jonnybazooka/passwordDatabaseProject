package org.sda.authentication.authService;

import org.sda.authentication.hashFunction.HashFunction;
import org.sda.user.User;

public class AuthServiceImplementation implements AuthService {
    /*@Override
    public boolean isAuthenticated(User user, HashFunction hashFunction) {
        String userHashedPassword = hashFunction.hash(user.getPassword());
        System.out.println("userHashedPassword: " + userHashedPassword);
        return hashFunction.hash(user.getPassword()).equals("2739c77e7c8bde2251179784b74dde2e08ae633d8b6485b1879dd759cc3c851a");
    }*/

    @Override
    public boolean isAuthenticated(User user, HashFunction hashFunction, String password) {
        String hashedPassword = hashFunction.hash(password);
        if (user != null) {
            return user.getPassword().equals(hashedPassword);
        }
        return false;
    }
}
