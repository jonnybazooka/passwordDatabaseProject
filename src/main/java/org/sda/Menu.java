package org.sda;

import org.sda.validation.Validators;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private char[] password;
    private List<User> users;
    private boolean isClosed;

    public Menu(List<User> users) {
        this.password = null;
        this.users = users;
        this.isClosed = false;
    }

    public void view1() {
        Console console = System.console();
        System.out.println("==============================");
        System.out.println("|    Enter user name:     ");
        String userName = console.readLine();
        System.out.println("==============================");
        System.out.println("Welcome user: " + userName);
        System.out.println("==============================");
        password = console.readPassword("|    Enter password:    ");
        System.out.println("==============================");
        for (User user : users) {
            if (user.getName().equals(userName)) {
                for (char[] pass : user.getPasswords()) {
                    if (new String(pass).equals(new String(password))) {
                        view2(user);
                    }
                }
            } else {
                System.out.println("User not recognised.");
                view3();
                isClosed = true;
                break;
            }
        }
    }

    public void view2(User user) {

        Scanner scanner = new Scanner(System.in);
        while (!isClosed) {
            System.out.println("=======================================");
            System.out.println("|    Hello user " + user.getName() + "                |");
            System.out.println("|    1. Show user stored passwords    |");
            System.out.println("|    2. Show user address             |");
            System.out.println("|    3. Log out                       |");
            System.out.println("=======================================");
            int digit = scanner.nextInt();
            switch (digit) {
                case 1:
                    user.getPasswords().stream()
                            .map(e -> new String(e))
                            .forEach(System.out::println);
                    break;
                case 2:
                    System.out.println(user.getAddress().toString());
                    break;
                case 3:
                    isClosed = false;
                    view3();
                    break;
                default:
                    System.out.println("Wrong input, try again");
                    break;
            }
        }
    }

    public void view3() {

        String name;
        char[] password;
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();

        while (!isClosed) {
            System.out.println("=======================================");
            System.out.println("|    1. Log In                        |");
            System.out.println("|    2. Register new user             |");
            System.out.println("|    3. List all users                |");
            System.out.println("|    4. Exit                          |");
            System.out.println("=======================================");
            int digit = scanner.nextInt();
            switch (digit) {
                case 1:
                    System.out.println(users);
                    scanner.nextLine();
                    System.out.println("Enter user name: ");
                    name = scanner.nextLine();
                    password = console.readPassword("Enter password: ");
                    for (User user : users) {
                        if (user.getName().equals(name)) {
                            for (char[] pass : user.getPasswords()) {
                                if (new String(pass).equals(new String(password))) {
                                    view2(user);
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case 2:
                    scanner.nextLine();
                    Validators validators = new Validators();
                    System.out.println("Enter new user's name: ");
                    name = scanner.nextLine();
                    password = console.readPassword("Enter new password for user: " + name);
                    if (!validators.validatePassword(new String(password))) {
                        System.out.println("Password must be 3-10 characters long, and contain at least 2 upper case characters.");
                        break;
                    }
                    System.out.println("Enter country: ");
                    String country = scanner.nextLine();
                    System.out.println("Enter city: ");
                    String city = scanner.nextLine();
                    System.out.println("Enter street: ");
                    String street = scanner.nextLine();
                    System.out.println("Enter house number: ");
                    int house = scanner.nextInt();
                    User newUser = new User(new Address(country, city, street, house), name);
                    newUser.addPassword(password);
                    users.add(newUser);
                    System.out.println("User registered successfully.");
                    view2(newUser);
                    isClosed = false;
                    break;
                case 3:
                    users.stream()
                            .map(e -> e.getName())
                            .forEach(System.out::println);
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    isClosed = true;
                    break;
                default:
                    System.out.println("Wrong input dude.");
                    break;
            }
        }
    }
}
