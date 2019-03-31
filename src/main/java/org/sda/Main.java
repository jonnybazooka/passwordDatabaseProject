package org.sda;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<User> userList = new ArrayList<>();
        User thomas = new User(new Address("Poland", "Bialystok", "Zielonogorska", 10), "Thomas");
        User ralph = new User(new Address("England", "York", "BelleVue", 6), "Ralph");
        User dexter = new User(new Address("USA", "Miami", "MurderAv", 66), "Dexter");
        thomas.addPassword("dupa".toCharArray());
        userList.add(thomas);
        userList.add(ralph);
        userList.add(dexter);

        Menu menu = new Menu();

        if (args.length == 0) {
            menu.view1(userList);
        } else if (args.length == 2) {
            for (User user : userList) {
                if (user.getName().equals(args[0])) {
                    for (char[] password : user.getPasswords()) {
                        if (new String(password).equals(args[1])) {
                            menu.view2(user, userList);
                        }
                    }
                } else {
                    menu.view3(userList);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }

    }
}
