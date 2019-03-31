package org.sda;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public void view1(List<User> users) {
        char[] password;
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
                        view2(user, users);
                    }
                }
            } else {
                System.out.println("User not recognised.");
                view3(users);
                break;
            }
        }
    }

    public void view2(User user, List<User> users) {

        Scanner scanner = new Scanner(System.in);
        boolean isClosed = false;
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
                    isClosed = true;
                    view3(users);
                    break;
                default:
                    System.out.println("Wrong input, try again");
                    break;
            }
        }
    }

    public void view3(List<User> users) {

        String name;
        char[] password;
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();
        boolean isClosed = false;

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
                    scanner.nextLine();
                    System.out.println("Enter user name: ");
                    name = scanner.nextLine();
                    password = console.readPassword("Enter password: ");
                    for (User user : users) {
                        if (user.getName().equals(name)) {
                            for (char[] pass : user.getPasswords()) {
                                if (new String(pass).equals(new String(password))) {
                                    view2(user, users);
                                    break;
                                }
                            }
                        } else {
                            System.out.println("User not recognised.");
                            view3(users);
                            break;
                        }
                    }
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.println("Enter new user's name: ");
                    name = scanner.nextLine();
                    password = console.readPassword("Enter new password for user: " + name);
                    System.out.println("Password taken: " + new String(password));
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
                    isClosed = true;
                    view2(newUser, users);
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
