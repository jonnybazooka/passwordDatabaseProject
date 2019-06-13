package comparators;

import org.junit.Before;
import org.junit.Test;
import org.sda.comparators.ReversedUserNameComparator;
import org.sda.comparators.UserNameAndAdressComparator;
import org.sda.comparators.UserNameComparatorScientistsFavoured;
import org.sda.user.Address;
import org.sda.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ComparatorsTests {
    /*private List<User> users = new ArrayList<>();
    private List<User> sortedUsers = new ArrayList<>();

    @Before
    public void createUsers() {
        User user1 = new User(new Address("Polska", "Bialystok", "Sikorskiego", 10), "Antoni");
        User user2 = new User(new Address("Polska", "Bialystok", "Wroclawska", 10), "Antoni");
        User user3 = new User(new Address("Polska", "Bialystok", "Sikorskiego", 10), "Borubar");
        User user4 = new User(new Address("USA", "New York", "World Trade Center", 12), "Abdul");
        user1.addPassword("MARYMONT66".toCharArray());
        user2.addPassword("MARYMONT66".toCharArray());
        user3.addPassword("MARYMONT66".toCharArray());
        user4.addPassword("MARYMONT66".toCharArray());
        user1.setScienceDegree("Professor");
        user3.setScienceDegree("Doctor");
        user4.setScienceDegree("Doctor");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
    }*/

    /*@Test
    public void scienceDegreeComparatorTest() {
        User user1 = new User(new Address("Polska", "Bialystok", "Sikorskiego", 10), "Antoni");
        User user2 = new User(new Address("Polska", "Bialystok", "Wroclawska", 10), "Antoni");
        User user3 = new User(new Address("Polska", "Bialystok", "Sikorskiego", 10), "Borubar");
        User user4 = new User(new Address("USA", "New York", "World Trade Center", 12), "Abdul");
        user1.addPassword("MARYMONT66".toCharArray());
        user2.addPassword("MARYMONT66".toCharArray());
        user3.addPassword("MARYMONT66".toCharArray());
        user4.addPassword("MARYMONT66".toCharArray());
        user1.setScienceDegree("Professor");
        user3.setScienceDegree("Doctor");
        user4.setScienceDegree("Doctor");
        sortedUsers.add(user4);
        sortedUsers.add(user1);
        sortedUsers.add(user3);
        sortedUsers.add(user2);
        users.sort(new UserNameComparatorScientistsFavoured());
        assertEquals(sortedUsers, users);
    }*/

    /*@Test
    public void reversedNameComparatorTest() {
        User user1 = new User(new Address("Polska", "Bialystok", "Sikorskiego", 10), "Antoni");
        User user2 = new User(new Address("Polska", "Bialystok", "Wroclawska", 10), "Antoni");
        User user3 = new User(new Address("Polska", "Bialystok", "Sikorskiego", 10), "Borubar");
        User user4 = new User(new Address("USA", "New York", "World Trade Center", 12), "Abdul");
        user1.addPassword("MARYMONT66".toCharArray());
        user2.addPassword("MARYMONT66".toCharArray());
        user3.addPassword("MARYMONT66".toCharArray());
        user4.addPassword("MARYMONT66".toCharArray());
        user1.setScienceDegree("Professor");
        user3.setScienceDegree("Doctor");
        user4.setScienceDegree("Doctor");
        sortedUsers.add(user3);
        sortedUsers.add(user1);
        sortedUsers.add(user2);
        sortedUsers.add(user4);
        users.sort(new ReversedUserNameComparator());
        assertEquals(sortedUsers, users);
    }*/

    /*@Test
    public void userNameAndAddressComparatorTest() {
        User user1 = new User(new Address("Polska", "Bialystok", "Sikorskiego", 10), "Antoni");
        User user2 = new User(new Address("Polska", "Bialystok", "Wroclawska", 10), "Antoni");
        User user3 = new User(new Address("Polska", "Bialystok", "Sikorskiego", 10), "Borubar");
        User user4 = new User(new Address("USA", "New York", "World Trade Center", 12), "Abdul");
        user1.addPassword("MARYMONT66".toCharArray());
        user2.addPassword("MARYMONT66".toCharArray());
        user3.addPassword("MARYMONT66".toCharArray());
        user4.addPassword("MARYMONT66".toCharArray());
        user1.setScienceDegree("Professor");
        user3.setScienceDegree("Doctor");
        user4.setScienceDegree("Doctor");
        sortedUsers.add(user4);
        sortedUsers.add(user1);
        sortedUsers.add(user2);
        sortedUsers.add(user3);
        users.sort(new UserNameAndAdressComparator());
        assertEquals(sortedUsers, users);
    }*/
}
