import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class TodoItemTaskDAOCollectionTest {

    private TodoItemTaskDAOCollection todoItemTaskDAO;
    private Person testCreator;
    private TodoItem testTodoItem;

    @BeforeEach
    void setUp() {
        todoItemTaskDAO = new TodoItemTaskDAOCollection();
        TodoItemTaskSequencer.setCurrentId(0);
        testCreator = new Person(1, "Test", "Creator", "test.creator@example.com");
        testTodoItem = new TodoItem(1, "Generic Task", "Description", LocalDate.now().plusDays(7), testCreator);
    }

    @Test
    void testPersistAndFindAll() {
        TodoItemTask task1 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, null);
        TodoItemTask task2 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, testCreator);

        todoItemTaskDAO.persist(task1);
        todoItemTaskDAO.persist(task2);

        Collection<TodoItemTask> allTasks = todoItemTaskDAO.findAll();
        assertEquals(2, allTasks.size());
        assertTrue(allTasks.contains(task1));
        assertTrue(allTasks.contains(task2));
    }

    @Test
    void testPersistDuplicateIdThrowsException() {
        TodoItemTask task1 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, null);
        todoItemTaskDAO.persist(task1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoItemTaskDAO.persist(new TodoItemTask(task1.getId(), testTodoItem, testCreator));
        });
        assertEquals("TodoItemTask with this ID already exists.", exception.getMessage());
    }

    @Test
    void testFindById() {
        TodoItemTask task1 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, null);
        todoItemTaskDAO.persist(task1);

        assertEquals(task1, todoItemTaskDAO.findById(task1.getId()));
        assertNull(todoItemTaskDAO.findById(999));
    }

    @Test
    void testFindByAssignedStatus() {
        TodoItemTask task1 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, null);
        TodoItemTask task2 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, testCreator);
        TodoItemTask task3 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, null);

        todoItemTaskDAO.persist(task1);
        todoItemTaskDAO.persist(task2);
        todoItemTaskDAO.persist(task3);

        Collection<TodoItemTask> assignedTasks = todoItemTaskDAO.findByAssignedStatus(true);
        assertEquals(1, assignedTasks.size());
        assertTrue(assignedTasks.contains(task2));

        Collection<TodoItemTask> unassignedTasks = todoItemTaskDAO.findByAssignedStatus(false);
        assertEquals(2, unassignedTasks.size());
        assertTrue(unassignedTasks.contains(task1));
        assertTrue(unassignedTasks.contains(task3));
    }

    @Test
    void testFindByPersonId() {
        Person assignee1 = new Person(10, "Assignee", "One", "one@example.com");
        Person assignee2 = new Person(20, "Assignee", "Two", "two@example.com");

        TodoItemTask task1 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, assignee1);
        TodoItemTask task2 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, assignee2);
        TodoItemTask task3 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, assignee1);
        TodoItemTask task4 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, null);

        todoItemTaskDAO.persist(task1);
        todoItemTaskDAO.persist(task2);
        todoItemTaskDAO.persist(task3);
        todoItemTaskDAO.persist(task4);

        Collection<TodoItemTask> tasksByAssignee1 = todoItemTaskDAO.findByPersonId(assignee1.getId());
        assertEquals(2, tasksByAssignee1.size());
        assertTrue(tasksByAssignee1.contains(task1));
        assertTrue(tasksByAssignee1.contains(task3));

        Collection<TodoItemTask> tasksByAssignee2 = todoItemTaskDAO.findByPersonId(assignee2.getId());
        assertEquals(1, tasksByAssignee2.size());
        assertTrue(tasksByAssignee2.contains(task2));

        Collection<TodoItemTask> tasksByNonExistentAssignee = todoItemTaskDAO.findByPersonId(999);
        assertTrue(tasksByNonExistentAssignee.isEmpty());

        Collection<TodoItemTask> tasksWithoutAssignee = todoItemTaskDAO.findByPersonId(0); // Assuming 0 is not a valid person ID
        assertTrue(tasksWithoutAssignee.isEmpty());
    }

    @Test
    void testRemove() {
        TodoItemTask task1 = new TodoItemTask(TodoItemTaskSequencer.nextId(), testTodoItem, null);
        todoItemTaskDAO.persist(task1);

        assertEquals(1, todoItemTaskDAO.findAll().size());

        todoItemTaskDAO.remove(task1.getId());
        assertEquals(0, todoItemTaskDAO.findAll().size());
        assertNull(todoItemTaskDAO.findById(task1.getId()));

        todoItemTaskDAO.remove(999); // Should not throw exception
        assertEquals(0, todoItemTaskDAO.findAll().size());
    }

    @Test
    void testPersistNullTodoItemTaskThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoItemTaskDAO.persist(null);
        });
        assertEquals("TodoItemTask cannot be null.", exception.getMessage());
    }
}
