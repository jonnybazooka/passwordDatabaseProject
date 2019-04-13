package org.sda.database;

import org.sda.exceptions.UserNotFoundException;
import org.sda.user.User;

import java.util.List;

public class UserServiceImplementation implements UserService{

    @Override
    public List<User> findAllUsers() {
        return DatabaseManager.getINSTANCE().getAllUsers();
    }

    @Override
    public User findUserByName(String userName) throws UserNotFoundException {
        return DatabaseManager.getINSTANCE().getUser(userName);
    }
}
