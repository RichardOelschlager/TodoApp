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
    void testGetSummary() {
        Person person = new Person(9, "Alice", "Wonderland", "alice@example.com");
        String expectedSummary = "{id: 9, name: Alice Wonderland, email: alice@example.com}";
        assertEquals(expectedSummary, person.getSummary());
    }

    @Test
    void testEqualsAndHashCode() {
        Person person1 = new Person(10, "Bob", "Builder", "bob@example.com");
        Person person2 = new Person(10, "Bob", "Builder", "bob@example.com");
        Person person3 = new Person(11, "Charlie", "Chaplin", "charlie@example.com");

        assertEquals(person1, person2);
        assertEquals(person1.hashCode(), person2.hashCode());
        assertNotEquals(person1, person3);
        assertNotEquals(person1.hashCode(), person3.hashCode());
    }

    @Test
    void testToString() {
        Person person = new Person(12, "Diana", "Prince", "diana@example.com");
        String expectedString = "Person{id=12, firstName='Diana', lastName='Prince', email='diana@example.com'}";
        assertEquals(expectedString, person.toString());
    }
}