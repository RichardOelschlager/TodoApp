import com.todoapp.Person;
import com.todoapp.data.PersonDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PersonDAOCollection implements PersonDAO {
    private Collection<Person> people;

    public PersonDAOCollection() {
        this.people = new ArrayList<>();
    }

    @Override
    public Person persist(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null.");
        }
        if (findById(person.getId()) != null) {
            throw new IllegalArgumentException("Person with this ID already exists.");
        }
        if (findByEmail(person.getEmail()) != null) {
            throw new IllegalArgumentException("Person with this email already exists.");
        }
        people.add(person);
        return person;
    }

    @Override
    public Person findById(int id) {
        Optional<Person> foundPerson = people.stream()
                .filter(person -> person.getId() == id)
                .findFirst();
        return foundPerson.orElse(null);
    }

    @Override
    public Person findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }
        Optional<Person> foundPerson = people.stream()
                .filter(person -> person.getEmail().equalsIgnoreCase(email))
                .findFirst();
        return foundPerson.orElse(null);
    }

    @Override
    public Collection<Person> findAll() {
        return new ArrayList<>(people);
    }

    @Override
    public void remove(int id) {
        people.removeIf(person -> person.getId() == id);
    }
}

