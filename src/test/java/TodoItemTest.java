import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TodoItemTest {

    private Person createTestPerson() {
        return new Person(1, "Test", "Creator", "test.creator@example.com");
    }

    @Test
    void testTodoItemCreation() {
        Person creator = createTestPerson();
        LocalDate deadline = LocalDate.now().plusDays(7);
        TodoItem todoItem = new TodoItem(1, "Buy groceries", "Milk, eggs, bread", deadline, creator);

        assertNotNull(todoItem);
        assertEquals(1, todoItem.getId());
        assertEquals("Buy groceries", todoItem.getTitle());
        assertEquals("Milk, eggs, bread", todoItem.getDescription());
        assertEquals(deadline, todoItem.getDeadLine());
        assertFalse(todoItem.isDone());
        assertEquals(creator, todoItem.getCreator());
    }

    @Test
    void testInvalidTitle() {
        Person creator = createTestPerson();
        LocalDate deadline = LocalDate.now().plusDays(7);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new TodoItem(2, null, "Description", deadline, creator);
        });
        assertEquals("Title cannot be null or empty.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new TodoItem(2, "", "Description", deadline, creator);
        });
        assertEquals("Title cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testInvalidDeadline() {
        Person creator = createTestPerson();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new TodoItem(3, "Title", "Description", null, creator);
        });
        assertEquals("Deadline cannot be null.", exception.getMessage());
    }

    @Test
    void testInvalidCreator() {
        LocalDate deadline = LocalDate.now().plusDays(7);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new TodoItem(4, "Title", "Description", deadline, null);
        });
        assertEquals("Creator cannot be null.", exception.getMessage());
    }

    @Test
    void testSetters() {
        Person creator = createTestPerson();
        LocalDate deadline = LocalDate.now().plusDays(7);
        TodoItem todoItem = new TodoItem(5, "Old Title", "Old Description", deadline, creator);

        todoItem.setTitle("New Title");
        assertEquals("New Title", todoItem.getTitle());

        todoItem.setDescription("New Description");
        assertEquals("New Description", todoItem.getDescription());

        LocalDate newDeadline = LocalDate.now().plusDays(14);
        todoItem.setDeadLine(newDeadline);
        assertEquals(newDeadline, todoItem.getDeadLine());

        todoItem.setDone(true);
        assertTrue(todoItem.isDone());

        Person newCreator = new Person(2, "New", "Creator", "new.creator@example.com");
        todoItem.setCreator(newCreator);
        assertEquals(newCreator, todoItem.getCreator());
    }

    @Test
    void testSetInvalidTitle() {
        Person creator = createTestPerson();
        LocalDate deadline = LocalDate.now().plusDays(7);
        TodoItem todoItem = new TodoItem(6, "Valid Title", "Description", deadline, creator);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoItem.setTitle(null);
        });
        assertEquals("Title cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testSetInvalidDeadline() {
        Person creator = createTestPerson();
        LocalDate deadline = LocalDate.now().plusDays(7);
        TodoItem todoItem = new TodoItem(7, "Valid Title", "Description", deadline, creator);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoItem.setDeadLine(null);
        });
        assertEquals("Deadline cannot be null.", exception.getMessage());
    }

    @Test
    void testSetInvalidCreator() {
        Person creator = createTestPerson();
        LocalDate deadline = LocalDate.now().plusDays(7);
        TodoItem todoItem = new TodoItem(8, "Valid Title", "Description", deadline, creator);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoItem.setCreator(null);
        });
        assertEquals("Creator cannot be null.", exception.getMessage());
    }

    @Test
    void testIsOverdue() {
        Person creator = createTestPerson();

        // Not overdue
        LocalDate futureDate = LocalDate.now().plusDays(1);
        TodoItem notOverdueItem = new TodoItem(9, "Future Task", "", futureDate, creator);
        assertFalse(notOverdueItem.isOverdue());

        // Overdue
        LocalDate pastDate = LocalDate.now().minusDays(1);
        TodoItem overdueItem = new TodoItem(10, "Past Task", "", pastDate, creator);
        assertTrue(overdueItem.isOverdue());

        // Today is not overdue
        LocalDate today = LocalDate.now();
        TodoItem todayItem = new TodoItem(11, "Today Task", "", today, creator);
        assertFalse(todayItem.isOverdue());
    }

    @Test
    void testGetSummary() {
        Person creator = createTestPerson();
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        TodoItem todoItem = new TodoItem(12, "Summary Test", "Test description", deadline, creator);
        String expectedSummary = String.format("{id: 12, title: Summary Test, description: Test description, deadLine: %s, done: false, creator: {id: 1, name: Test Creator, email: test.creator@example.com}}", deadline);
        assertEquals(expectedSummary, todoItem.getSummary());
    }

    @Test
    void testEqualsAndHashCode() {
        Person creator1 = createTestPerson();
        Person creator2 = new Person(2, "Another", "Creator", "another.creator@example.com");
        LocalDate deadline1 = LocalDate.of(2025, 1, 1);
        LocalDate deadline2 = LocalDate.of(2025, 1, 2);

        TodoItem item1 = new TodoItem(1, "Title", "Desc", deadline1, creator1);
        TodoItem item2 = new TodoItem(1, "Title", "Desc", deadline1, creator1);
        TodoItem item3 = new TodoItem(2, "Title", "Desc", deadline1, creator1);
        TodoItem item4 = new TodoItem(1, "Different Title", "Desc", deadline1, creator1);
        TodoItem item5 = new TodoItem(1, "Title", "Different Desc", deadline1, creator1);
        TodoItem item6 = new TodoItem(1, "Title", "Desc", deadline2, creator1);
        TodoItem item7 = new TodoItem(1, "Title", "Desc", deadline1, creator2);

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());

        assertNotEquals(item1, item3);
        assertNotEquals(item1, item4);
        assertNotEquals(item1, item5);
        assertNotEquals(item1, item6);
        assertNotEquals(item1, item7);
    }

    @Test
    void testToString() {
        Person creator = createTestPerson();
        LocalDate deadline = LocalDate.of(2025, 1, 1);
        TodoItem todoItem = new TodoItem(1, "Test String", "Description", deadline, creator);
        String expectedString = "TodoItem{id=1, title=\'Test String\', description=\'Description\', deadLine=2025-01-01, done=false, creator=Person{id=1, firstName=\'Test\', lastName=\'Creator\', email=\'test.creator@example.com\'}}";
        assertEquals(expectedString, todoItem.toString());
    }
}