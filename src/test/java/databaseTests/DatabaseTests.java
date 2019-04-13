package databaseTests;

import org.junit.Test;
import org.sda.database.DatabaseManager;

import static org.junit.Assert.assertSame;

public class DatabaseTests {

    @Test
    public void singletonTest() {
        DatabaseManager databaseManager1 = DatabaseManager.getINSTANCE();
        DatabaseManager databaseManager2 = DatabaseManager.getINSTANCE();
        assertSame("Objects must have the same memory reference", databaseManager1, databaseManager2);
    }
}
