import java.util.Objects;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private AppUser credentials;

    public Person(int id, String firstName, String lastName, String email) {
        this(id, firstName, lastName, email, null);
    }

    public Person(int id, String firstName, String lastName, String email, AppUser credentials) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.credentials = credentials;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        this.email = email;
    }

    public AppUser getCredentials() {
        return credentials;
    }

    public void setCredentials(AppUser credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName=\'" + firstName + "\'" +
                ", lastName=\'" + lastName + "\'" +
                ", email=\'" + email + "\'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }
}

