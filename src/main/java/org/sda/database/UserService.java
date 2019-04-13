package org.sda.database;

import org.sda.exceptions.UserNotFoundException;
import org.sda.user.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findUserByName(String userName) throws UserNotFoundException;
}
