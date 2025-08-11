import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    void testPersonCreation() {
        Person person = new Person(1, "John", "Doe", "john.doe@example.com");
        assertNotNull(person);
        assertEquals(1, person.getId());
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals("john.doe@example.com", person.getEmail());
        assertNull(person.getCredentials());
    }

    @Test
    void testPersonCreationWithCredentials() {
        AppUser appUser = new AppUser("johndoe", "password", AppRole.ROLE_APP_USER);
        Person person = new Person(1, "John", "Doe", "john.doe@example.com", appUser);
        assertNotNull(person);
        assertEquals(appUser, person.getCredentials());
    }

    @Test
    void testInvalidFirstName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person(2, null, "Doe", "john.doe@example.com");
        });
        assertEquals("First name cannot be null or empty.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person(2, "", "Doe", "john.doe@example.com");
        });
        assertEquals("First name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testInvalidLastName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person(3, "John", null, "john.doe@example.com");
        });
        assertEquals("Last name cannot be null or empty.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person(3, "John", "", "john.doe@example.com");
        });
        assertEquals("Last name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testInvalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person(4, "John", "Doe", null);
        });
        assertEquals("Email cannot be null or empty.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person(4, "John", "Doe", "");
        });
        assertEquals("Email cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testSetters() {
        Person person = new Person(5, "Jane", "Smith", "jane.smith@example.com");
        person.setFirstName("Janet");
        assertEquals("Janet", person.getFirstName());
        person.setLastName("Jones");
        assertEquals("Jones", person.getLastName());
        person.setEmail("janet.jones@example.com");
        assertEquals("janet.jones@example.com", person.getEmail());

        AppUser appUser = new AppUser("janetj", "pass", AppRole.ROLE_APP_USER);
        person.setCredentials(appUser);
        assertEquals(appUser, person.getCredentials());
    }

    @Test
    void testSetInvalidFirstName() {
        Person person = new Person(6, "Test", "User", "test.user@example.com");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            person.setFirstName(null);
        });
        assertEquals("First name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testSetInvalidLastName() {
        Person person = new Person(7, "Test", "User", "test.user@example.com");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            person.setLastName(null);
        });
        assertEquals("Last name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testSetInvalidEmail() {
        Person person = new Person(8, "Test", "User", "test.user@example.com");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            person.setEmail(null);
        });
        assertEquals("Email cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testToString() {
        Person person = new Person(9, "Alice", "Wonderland", "alice@example.com");
        String expectedString = "Person{id=9, firstName=\'Alice\', lastName=\'Wonderland\', email=\'alice@example.com\'}";
        assertEquals(expectedString, person.toString());

        AppUser appUser = new AppUser("aliceW", "pass", AppRole.ROLE_APP_USER);
        Person personWithCreds = new Person(10, "Bob", "Builder", "bob@example.com", appUser);
        String expectedStringWithCreds = "Person{id=10, firstName=\'Bob\', lastName=\'Builder\', email=\'bob@example.com\'}";
        assertEquals(expectedStringWithCreds, personWithCreds.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Person person1 = new Person(11, "Bob", "Builder", "bob@example.com");
        Person person2 = new Person(11, "Bob", "Builder", "bob@example.com");
        Person person3 = new Person(12, "Charlie", "Chaplin", "charlie@example.com");

        AppUser appUser1 = new AppUser("bobB", "pass1", AppRole.ROLE_APP_USER);
        AppUser appUser2 = new AppUser("bobB", "pass2", AppRole.ROLE_APP_ADMIN);

        Person person4 = new Person(11, "Bob", "Builder", "bob@example.com", appUser1);
        Person person5 = new Person(11, "Bob", "Builder", "bob@example.com", appUser2);

        assertEquals(person1, person2);
        assertEquals(person1.hashCode(), person2.hashCode());
        assertNotEquals(person1, person3);
        assertNotEquals(person1.hashCode(), person3.hashCode());

        // Test with credentials - credentials should be excluded from equals and hashCode
        assertEquals(person4, person5);
        assertEquals(person4.hashCode(), person5.hashCode());
    }
}