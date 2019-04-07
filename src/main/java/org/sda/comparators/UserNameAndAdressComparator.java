package org.sda.comparators;

import org.sda.user.User;

import java.util.Comparator;

public class UserNameAndAdressComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        int result = o1.getName().compareTo(o2.getName());
        if (result == 0) {
            result = o1.getAddress().getCountry().compareTo(o2.getAddress().getCountry());
            if (result == 0) {
                result = o1.getAddress().getCity().compareTo(o2.getAddress().getCity());
                if (result == 0) {
                    result = o1.getAddress().getStreet().compareTo(o2.getAddress().getStreet());
                    if (result == 0) {
                        result = o1.getAddress().getHouseNumber() - o2.getAddress().getHouseNumber();
                    }
                }
            }
        }
        return result;
    }
}
