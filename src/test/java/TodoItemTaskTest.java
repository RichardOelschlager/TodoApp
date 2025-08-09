import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TodoItemTaskTest {

    private Person createTestPerson(int id, String firstName, String lastName, String email) {
        return new Person(id, firstName, lastName, email);
    }

    private TodoItem createTestTodoItem(int id, String title, String description, LocalDate deadLine, Person creator) {
        return new TodoItem(id, title, description, deadLine, creator);
    }

    @Test
    void testTodoItemTaskCreation() {
        Person creator = createTestPerson(1, "John", "Doe", "john.doe@example.com");
        TodoItem todoItem = createTestTodoItem(1, "Task Title", "Task Description", LocalDate.now().plusDays(1), creator);
        Person assignee = createTestPerson(2, "Jane", "Smith", "jane.smith@example.com");

        TodoItemTask task = new TodoItemTask(1, todoItem, assignee);

        assertNotNull(task);
        assertEquals(1, task.getId());
        assertEquals(todoItem, task.getTodoItem());
        assertEquals(assignee, task.getAssignee());
        assertTrue(task.isAssigned());
    }

    @Test
    void testTodoItemTaskCreationWithoutAssignee() {
        Person creator = createTestPerson(1, "John", "Doe", "john.doe@example.com");
        TodoItem todoItem = createTestTodoItem(1, "Task Title", "Task Description", LocalDate.now().plusDays(1), creator);

        TodoItemTask task = new TodoItemTask(2, todoItem, null);

        assertNotNull(task);
        assertEquals(2, task.getId());
        assertEquals(todoItem, task.getTodoItem());
        assertNull(task.getAssignee());
        assertFalse(task.isAssigned());
    }

    @Test
    void testInvalidTodoItem() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new TodoItemTask(3, null, null);
        });
        assertEquals("TodoItem cannot be null.", exception.getMessage());
    }

    @Test
    void testSetters() {
        Person creator = createTestPerson(1, "John", "Doe", "john.doe@example.com");
        TodoItem todoItem1 = createTestTodoItem(1, "Task 1", "Desc 1", LocalDate.now().plusDays(1), creator);
        TodoItem todoItem2 = createTestTodoItem(2, "Task 2", "Desc 2", LocalDate.now().plusDays(2), creator);
        Person assignee1 = createTestPerson(2, "Jane", "Smith", "jane.smith@example.com");
        Person assignee2 = createTestPerson(3, "Peter", "Jones", "peter.jones@example.com");

        TodoItemTask task = new TodoItemTask(4, todoItem1, assignee1);

        task.setTodoItem(todoItem2);
        assertEquals(todoItem2, task.getTodoItem());

        task.setAssignee(assignee2);
        assertEquals(assignee2, task.getAssignee());
        assertTrue(task.isAssigned());

        task.setAssignee(null);
        assertNull(task.getAssignee());
        assertFalse(task.isAssigned());
    }

    @Test
    void testSetInvalidTodoItem() {
        Person creator = createTestPerson(1, "John", "Doe", "john.doe@example.com");
        TodoItem todoItem = createTestTodoItem(1, "Task Title", "Task Description", LocalDate.now().plusDays(1), creator);
        TodoItemTask task = new TodoItemTask(5, todoItem, null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            task.setTodoItem(null);
        });
        assertEquals("TodoItem cannot be null.", exception.getMessage());
    }

    @Test
    void testGetSummary() {
        Person creator = createTestPerson(1, "John", "Doe", "john.doe@example.com");
        TodoItem todoItem = createTestTodoItem(1, "Summary Task", "Summary Desc", LocalDate.now().plusDays(1), creator);
        Person assignee = createTestPerson(2, "Jane", "Smith", "jane.smith@example.com");

        TodoItemTask taskWithAssignee = new TodoItemTask(6, todoItem, assignee);
        String expectedSummaryWithAssignee = String.format("{id: 6, assigned: true, todoItem: %s, assignee: %s}",
                todoItem.getSummary(), assignee.getSummary());
        assertEquals(expectedSummaryWithAssignee, taskWithAssignee.getSummary());

        TodoItemTask taskWithoutAssignee = new TodoItemTask(7, todoItem, null);
        String expectedSummaryWithoutAssignee = String.format("{id: 7, assigned: false, todoItem: %s, assignee: null}",
                todoItem.getSummary());
        assertEquals(expectedSummaryWithoutAssignee, taskWithoutAssignee.getSummary());
    }

    @Test
    void testEqualsAndHashCode() {
        Person creator = createTestPerson(1, "John", "Doe", "john.doe@example.com");
        TodoItem todoItem1 = createTestTodoItem(1, "Task 1", "Desc 1", LocalDate.now().plusDays(1), creator);
        TodoItem todoItem2 = createTestTodoItem(2, "Task 2", "Desc 2", LocalDate.now().plusDays(2), creator);
        Person assignee1 = createTestPerson(2, "Jane", "Smith", "jane.smith@example.com");
        Person assignee2 = createTestPerson(3, "Peter", "Jones", "peter.jones@example.com");

        TodoItemTask task1 = new TodoItemTask(1, todoItem1, assignee1);
        TodoItemTask task2 = new TodoItemTask(1, todoItem1, assignee1);
        TodoItemTask task3 = new TodoItemTask(2, todoItem1, assignee1);
        TodoItemTask task4 = new TodoItemTask(1, todoItem2, assignee1);
        TodoItemTask task5 = new TodoItemTask(1, todoItem1, assignee2);
        TodoItemTask task6 = new TodoItemTask(1, todoItem1, null);

        assertEquals(task1, task2);
        assertEquals(task1.hashCode(), task2.hashCode());

        assertNotEquals(task1, task3);
        assertNotEquals(task1, task4);
        assertNotEquals(task1, task5);
        assertNotEquals(task1, task6);
    }

    @Test
    void testToString() {
        Person creator = createTestPerson(1, "John", "Doe", "john.doe@example.com");
        TodoItem todoItem = createTestTodoItem(1, "Task Title", "Task Description", LocalDate.now().plusDays(1), creator);
        Person assignee = createTestPerson(2, "Jane", "Smith", "jane.smith@example.com");

        TodoItemTask task = new TodoItemTask(1, todoItem, assignee);
        String expectedString = "TodoItemTask{id=1, todoItem=TodoItem{id=1, title=\'Task Title\', description=\'Task Description\', deadLine=" + LocalDate.now().plusDays(1) + ", done=false, creator=Person{id=1, firstName=\'John\', lastName=\'Doe\', email=\'john.doe@example.com\'}}, assignee=Person{id=2, firstName=\'Jane\', lastName=\'Smith\', email=\'jane.smith@example.com\'}}";
        assertEquals(expectedString, task.toString());

        TodoItemTask taskWithoutAssignee = new TodoItemTask(2, todoItem, null);
        String expectedStringWithoutAssignee = "TodoItemTask{id=2, todoItem=TodoItem{id=1, title=\'Task Title\', description=\'Task Description\', deadLine=" + LocalDate.now().plusDays(1) + ", done=false, creator=Person{id=1, firstName=\'John\', lastName=\'Doe\', email=\'john.doe@example.com\'}}, assignee=null}";
        assertEquals(expectedStringWithoutAssignee, taskWithoutAssignee.toString());
    }
}