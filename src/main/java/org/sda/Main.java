package org.sda;

import org.sda.user.Address;
import org.sda.user.User;

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

        Menu menu;
        if (args.length == 0) {
            menu = new Menu(userList);
        } else if (args.length == 2) {
            menu = new Menu(userList, args[0], args[1]);
        } else {
            throw new IllegalArgumentException();
        }
        menu.menuController();
    }
}
