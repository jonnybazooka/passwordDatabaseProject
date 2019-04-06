package org.sda;

import org.sda.user.Address;
import org.sda.user.User;
import org.sda.validation.Validators;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private char[] password;
    private List<User> users;
    private User currentUser;
    private boolean shutdown;
    private boolean isUserLoggedIn;

    public Menu(List<User> users) {
        this.password = null;
        this.users = users;
        this.shutdown = false;
        this.isUserLoggedIn = false;
        this.currentUser = null;
    }

    public Menu(List<User> users, String name, String password) {
        this.users = users;
        this.password = password.toCharArray();
        if (isPasswordCorrect(name)) {
            this.currentUser = getUser(name);
            this.isUserLoggedIn = true;
            this.shutdown = false;
        } else {
            System.out.println("User not recognised.");
            this.currentUser = null;
            this.isUserLoggedIn = false;
            this.shutdown = false;
            this.password = null;
        }
    }

    public void menuController() {
        if (isUserLoggedIn) {
            userView();
        } else {
            loginView();
        }
        do {
            if (isUserLoggedIn) {
                userView();
            } else {
                mainMenuView();
            }
        } while (!shutdown);
    }

    private void userView() {
        final int SHOW_USER_PASSWORDS = 1;
        final int CHANGE_MASTER_PASSWORD = 2;
        final int SHOW_USER_ADDRESS = 3;
        final int CHANGE_ADDRESS = 4;
        final int LOG_OUT = 5;
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======================================");
        System.out.println("|    Hello user " + currentUser.getName());
        System.out.println("|    1. Show user stored passwords    |");
        System.out.println("|    2. Change master password        |");
        System.out.println("|    3. Show user address             |");
        System.out.println("|    4. Change address                |");
        System.out.println("|    5. Log out                       |");
        System.out.println("=======================================");
        int digit = scanner.nextInt();
        switch (digit) {
            case SHOW_USER_PASSWORDS:
                currentUser.getPasswords().stream()
                        .map(e -> new String(e))
                        .forEach(System.out::println);
                break;
            case CHANGE_MASTER_PASSWORD:
                if (currentUser.changePassword()) {
                    isUserLoggedIn = false;
                    currentUser = null;
                }
                break;
            case SHOW_USER_ADDRESS:
                System.out.println(currentUser.getAddress().toString());
                break;
            case CHANGE_ADDRESS:
                currentUser.getAddress().changeAddress();
                break;
            case LOG_OUT:
                isUserLoggedIn = false;
                currentUser = null;
                break;
            default:
                System.out.println("Wrong input, try again");
                break;
        }
    }

    private void mainMenuView() {
        final int LOG_IN = 1;
        final int REGISTER_NEW_USER = 2;
        final int LIST_ALL_USERS = 3;
        final int SHOW_PUBLIC_INFORMATION = 4;
        final int EXIT = 5;
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======================================");
        System.out.println("|    1. Log In                        |");
        System.out.println("|    2. Register new user             |");
        System.out.println("|    3. List all users                |");
        System.out.println("|    4. Show public information       |");
        System.out.println("|    5. Exit                          |");
        System.out.println("=======================================");
        int digit = scanner.nextInt();
        switch (digit) {
            case LOG_IN:
                loginView();
                break;
            case REGISTER_NEW_USER:
                registerNewUser();
                break;
            case LIST_ALL_USERS:
                listAllUsers();
                break;
            case SHOW_PUBLIC_INFORMATION:
                currentUser = getUser();
                if (currentUser != null) {
                    System.out.println(currentUser.toString());
                    currentUser = null;
                } else {
                    System.out.println("User name not recognised.");
                }
                break;
            case EXIT:
                System.out.println("Goodbye!");
                shutdown = true;
                break;
            default:
                System.out.println("Wrong input.");
                break;
        }
    }

    private void registerNewUser() {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();
        Validators validators = new Validators();
        System.out.println("|    Enter new user's name: ");
        String name = scanner.nextLine();
        if (!validators.validateUserName(name)) {
            System.out.println("Name must be 5-15 characters long, and cannot start with a digit.");
        } else {
            password = console.readPassword("|    Enter new password for user " + name + " :");
            if (!validators.validatePassword(new String(password))) {
                System.out.println("Password must be 7-15 characters long, and contain at least 2 upper case characters, and 2 digits.");
            } else {
                Address address = new Address();
                address.changeAddress();
                User newUser = new User(address, name);
                newUser.addPassword(password);
                users.add(newUser);
                System.out.println("User " + newUser.getName() + " registered successfully.");
            }
        }
        password = null;
    }

    private void listAllUsers() {
        users.stream()
                .map(e -> e.getName())
                .forEach(System.out::println);
    }

    private void loginView() {
        Console console = System.console();
        System.out.println("==============================");
        System.out.println("|        LOGIN SCREEN        |");
        System.out.println("|    Enter user name:     ");
        String userName = console.readLine();
        password = console.readPassword("|    Enter password:    ");
        if (isPasswordCorrect(userName)) {
            isUserLoggedIn = true;
            currentUser = getUser(userName);
        } else {
            System.out.println("User not recognised.");
        }
    }

    private boolean isPasswordCorrect(String userName) {
        for (User user : users) {
            if (user.getName().equals(userName)) {
                for (char[] pass : user.getPasswords()) {
                    if (new String(pass).equals(new String(password))) {
                        return true;
                    }
                }
            }
        }
        password = null;
        return false;
    }

    private User getUser(String userName) {
        for (User user : users) {
            if (user.getName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    private User getUser() {
        System.out.println("|    Enter user's name: ");
        Scanner scanner = new Scanner(System.in);
        String user = scanner.nextLine();
        return getUser(user);
    }
}
