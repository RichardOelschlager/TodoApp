
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOCollectionTest {

    private PersonDAOCollection personDAO;

    @BeforeEach
    void setUp() {
        personDAO = new PersonDAOCollection();
        PersonIdSequencer.setCurrentId(0);
    }

    @Test
    void testPersistAndFindAll() {
        Person person1 = new Person(PersonIdSequencer.nextId(), "John", "Doe", "john.doe@example.com");
        Person person2 = new Person(PersonIdSequencer.nextId(), "Jane", "Smith", "jane.smith@example.com");

        personDAO.persist(person1);
        personDAO.persist(person2);

        Collection<Person> allPeople = personDAO.findAll();
        assertEquals(2, allPeople.size());
        assertTrue(allPeople.contains(person1));
        assertTrue(allPeople.contains(person2));
    }

    @Test
    void testPersistDuplicateIdThrowsException() {
        Person person1 = new Person(PersonIdSequencer.nextId(), "John", "Doe", "john.doe@example.com");
        personDAO.persist(person1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personDAO.persist(new Person(person1.getId(), "Jane", "Smith", "jane.smith@example.com"));
        });
        assertEquals("Person with this ID already exists.", exception.getMessage());
    }

    @Test
    void testPersistDuplicateEmailThrowsException() {
        Person person1 = new Person(PersonIdSequencer.nextId(), "John", "Doe", "john.doe@example.com");
        personDAO.persist(person1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personDAO.persist(new Person(PersonIdSequencer.nextId(), "Jane", "Smith", "john.doe@example.com"));
        });
        assertEquals("Person with this email already exists.", exception.getMessage());
    }

    @Test
    void testFindById() {
        Person person1 = new Person(PersonIdSequencer.nextId(), "John", "Doe", "john.doe@example.com");
        personDAO.persist(person1);

        assertEquals(person1, personDAO.findById(person1.getId()));
        assertNull(personDAO.findById(999));
    }

    @Test
    void testFindByEmail() {
        Person person1 = new Person(PersonIdSequencer.nextId(), "John", "Doe", "john.doe@example.com");
        personDAO.persist(person1);

        assertEquals(person1, personDAO.findByEmail("john.doe@example.com"));
        assertNull(personDAO.findByEmail("nonexistent@example.com"));
    }

    @Test
    void testRemove() {
        Person person1 = new Person(PersonIdSequencer.nextId(), "John", "Doe", "john.doe@example.com");
        personDAO.persist(person1);

        assertEquals(1, personDAO.findAll().size());

        personDAO.remove(person1.getId());
        assertEquals(0, personDAO.findAll().size());
        assertNull(personDAO.findById(person1.getId()));

        personDAO.remove(999); // Should not throw exception
        assertEquals(0, personDAO.findAll().size());
    }

    @Test
    void testPersistNullPersonThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personDAO.persist(null);
        });
        assertEquals("Person cannot be null.", exception.getMessage());
    }

    @Test
    void testFindByEmailNullThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personDAO.findByEmail(null);
        });
        assertEquals("Email cannot be null.", exception.getMessage());
    }
}