package org.sda.database;

import org.sda.exceptions.DatabaseIntegrityException;
import org.sda.exceptions.PasswordNotFoundException;
import org.sda.exceptions.UserNotFoundException;
import org.sda.user.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findUserByName(String userName) throws UserNotFoundException;
    User findUserByEmail(String email) throws UserNotFoundException;
    boolean createUser() throws DatabaseIntegrityException;
    boolean removeUser(User user) throws DatabaseIntegrityException;
    boolean editUsersAddress(User user) throws DatabaseIntegrityException;
    boolean showUsersPasswords(User user) throws PasswordNotFoundException, UserNotFoundException;
    boolean addNewPassword(User user);
    void editPassword(User user);
    void deletePassword(User user);
}
