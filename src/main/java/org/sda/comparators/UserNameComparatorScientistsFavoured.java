package org.sda.comparators;

import org.sda.user.User;

import java.util.Comparator;

public class UserNameComparatorScientistsFavoured implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        int result = 0;
        if (o1.getScienceDegree() != null && o2.getScienceDegree() != null) {
            result = o1.getName().compareTo(o2.getName());
            if (result == 0) {
                return o1.getScienceDegree().compareTo(o2.getScienceDegree());
            }
        } else if (o1.getScienceDegree() == null && o2.getScienceDegree() != null) {
            return 1000;
        } else if (o1.getScienceDegree() != null && o2.getScienceDegree() == null) {
            return -1000;
        } else {
            result = o1.getName().compareTo(o2.getName());
        }
        return result;
    }
}
