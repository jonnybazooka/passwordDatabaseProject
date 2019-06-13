package org.sda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sda.authentication.authService.AuthService;
import org.sda.authentication.authService.AuthServiceImplementation;
import org.sda.database.UserService;
import org.sda.database.UserServiceImplementation;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());
    private static AuthService authService = new AuthServiceImplementation();
    private static UserService userService = new UserServiceImplementation();

    public static void main(String[] args) {

        Menu menu;
        if (args.length == 0) {
            menu = new Menu(userService, authService);
        } else if (args.length == 2) {
            menu = new Menu(args[0], args[1], userService, authService);
        } else {
            throw new IllegalArgumentException();
        }
        LOGGER.debug("App starting.");
        menu.menuController();
        LOGGER.debug("App finishing.");
    }
}
