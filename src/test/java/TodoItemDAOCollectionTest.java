import com.todoapp.Person;
import com.todoapp.TodoItem;
import com.todoapp.sequencer.TodoItemIdSequencer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class TodoItemDAOCollectionTest {

    private TodoItemDAOCollection todoItemDAO;
    private Person testCreator;

    @BeforeEach
    void setUp() {
        todoItemDAO = new TodoItemDAOCollection();
        TodoItemIdSequencer.setCurrentId(0);
        testCreator = new Person(1, "Test", "Creator", "test.creator@example.com");
    }

    @Test
    void testPersistAndFindAll() {
        TodoItem item1 = new TodoItem(TodoItemIdSequencer.nextId(), "Title 1", "Desc 1", LocalDate.now().plusDays(1), testCreator);
        TodoItem item2 = new TodoItem(TodoItemIdSequencer.nextId(), "Title 2", "Desc 2", LocalDate.now().plusDays(2), testCreator);

        todoItemDAO.persist(item1);
        todoItemDAO.persist(item2);

        Collection<TodoItem> allItems = todoItemDAO.findAll();
        assertEquals(2, allItems.size());
        assertTrue(allItems.contains(item1));
        assertTrue(allItems.contains(item2));
    }

    @Test
    void testPersistDuplicateIdThrowsException() {
        TodoItem item1 = new TodoItem(TodoItemIdSequencer.nextId(), "Title 1", "Desc 1", LocalDate.now().plusDays(1), testCreator);
        todoItemDAO.persist(item1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoItemDAO.persist(new TodoItem(item1.getId(), "Title 2", "Desc 2", LocalDate.now().plusDays(2), testCreator));
        });
        assertEquals("TodoItem with this ID already exists.", exception.getMessage());
    }

    @Test
    void testFindById() {
        TodoItem item1 = new TodoItem(TodoItemIdSequencer.nextId(), "Title 1", "Desc 1", LocalDate.now().plusDays(1), testCreator);
        todoItemDAO.persist(item1);

        assertEquals(item1, todoItemDAO.findById(item1.getId()));
        assertNull(todoItemDAO.findById(999));
    }

    @Test
    void testFindAllByDoneStatus() {
        TodoItem item1 = new TodoItem(TodoItemIdSequencer.nextId(), "Title 1", "Desc 1", LocalDate.now().plusDays(1), testCreator);
        TodoItem item2 = new TodoItem(TodoItemIdSequencer.nextId(), "Title 2", "Desc 2", LocalDate.now().plusDays(2), testCreator);
        item2.setDone(true);
        TodoItem item3 = new TodoItem(TodoItemIdSequencer.nextId(), "Title 3", "Desc 3", LocalDate.now().plusDays(3), testCreator);

        todoItemDAO.persist(item1);
        todoItemDAO.persist(item2);
        todoItemDAO.persist(item3);

        Collection<TodoItem> doneItems = todoItemDAO.findAllByDoneStatus(true);
        assertEquals(1, doneItems.size());
        assertTrue(doneItems.contains(item2));

        Collection<TodoItem> notDoneItems = todoItemDAO.findAllByDoneStatus(false);
        assertEquals(2, notDoneItems.size());
        assertTrue(notDoneItems.contains(item1));
        assertTrue(notDoneItems.contains(item3));
    }

    @Test
    void testFindByTitleContains() {
        TodoItem item1 = new TodoItem(TodoItemIdSequencer.nextId(), "Buy milk", "", LocalDate.now().plusDays(1), testCreator);
        TodoItem item2 = new TodoItem(TodoItemIdSequencer.nextId(), "Walk dog", "", LocalDate.now().plusDays(2), testCreator);
        TodoItem item3 = new TodoItem(TodoItemIdSequencer.nextId(), "Buy bread", "", LocalDate.now().plusDays(3), testCreator);

        todoItemDAO.persist(item1);
        todoItemDAO.persist(item2);
        todoItemDAO.persist(item3);

        Collection<TodoItem> results = todoItemDAO.findByTitleContains("buy");
        assertEquals(2, results.size());
        assertTrue(results.contains(item1));
        assertTrue(results.contains(item3));

        results = todoItemDAO.findByTitleContains("dog");
        assertEquals(1, results.size());
        assertTrue(results.contains(item2));

        results = todoItemDAO.findByTitleContains("nonexistent");
        assertTrue(results.isEmpty());
    }

    @Test
    void testFindByPersonId() {
        Person creator1 = new Person(10, "Creator", "One", "one@example.com");
        Person creator2 = new Person(20, "Creator", "Two", "two@example.com");

        TodoItem item1 = new TodoItem(TodoItemIdSequencer.nextId(), "Task A", "", LocalDate.now().plusDays(1), creator1);
        TodoItem item2 = new TodoItem(TodoItemIdSequencer.nextId(), "Task B", "", LocalDate.now().plusDays(2), creator2);
        TodoItem item3 = new TodoItem(TodoItemIdSequencer.nextId(), "Task C", "", LocalDate.now().plusDays(3), creator1);

        todoItemDAO.persist(item1);
        todoItemDAO.persist(item2);
        todoItemDAO.persist(item3);

        Collection<TodoItem> itemsByCreator1 = todoItemDAO.findByPersonId(creator1.getId());
        assertEquals(2, itemsByCreator1.size());
        assertTrue(itemsByCreator1.contains(item1));
        assertTrue(itemsByCreator1.contains(item3));

        Collection<TodoItem> itemsByCreator2 = todoItemDAO.findByPersonId(creator2.getId());
        assertEquals(1, itemsByCreator2.size());
        assertTrue(itemsByCreator2.contains(item2));

        Collection<TodoItem> itemsByNonExistentCreator = todoItemDAO.findByPersonId(999);
        assertTrue(itemsByNonExistentCreator.isEmpty());
    }

    @Test
    void testFindByDeadlineBefore() {
        LocalDate today = LocalDate.now();
        TodoItem item1 = new TodoItem(TodoItemIdSequencer.nextId(), "Task 1", "", today.plusDays(5), testCreator);
        TodoItem item2 = new TodoItem(TodoItemIdSequencer.nextId(), "Task 2", "", today.minusDays(2), testCreator);
        TodoItem item3 = new TodoItem(TodoItemIdSequencer.nextId(), "Task 3", "", today.plusDays(1), testCreator);

        todoItemDAO.persist(item1);
        todoItemDAO.persist(item2);
        todoItemDAO.persist(item3);

        Collection<TodoItem> beforeToday = todoItemDAO.findByDeadlineBefore(today);
        assertEquals(1, beforeToday.size());
        assertTrue(beforeToday.contains(item2));

        Collection<TodoItem> beforeFutureDate = todoItemDAO.findByDeadlineBefore(today.plusDays(2));
        assertEquals(2, beforeFutureDate.size());
        assertTrue(beforeFutureDate.contains(item2));
        assertTrue(beforeFutureDate.contains(item3));
    }

    @Test
    void testFindByDeadlineAfter() {
        LocalDate today = LocalDate.now();
        TodoItem item1 = new TodoItem(TodoItemIdSequencer.nextId(), "Task 1", "", today.plusDays(5), testCreator);
        TodoItem item2 = new TodoItem(TodoItemIdSequencer.nextId(), "Task 2", "", today.minusDays(2), testCreator);
        TodoItem item3 = new TodoItem(TodoItemIdSequencer.nextId(), "Task 3", "", today.plusDays(1), testCreator);

        todoItemDAO.persist(item1);
        todoItemDAO.persist(item2);
        todoItemDAO.persist(item3);

        Collection<TodoItem> afterToday = todoItemDAO.findByDeadlineAfter(today);
        assertEquals(2, afterToday.size());
        assertTrue(afterToday.contains(item1));
        assertTrue(afterToday.contains(item3));

        Collection<TodoItem> afterPastDate = todoItemDAO.findByDeadlineAfter(today.minusDays(3));
        assertEquals(3, afterPastDate.size());
    }

    @Test
    void testRemove() {
        TodoItem item1 = new TodoItem(TodoItemIdSequencer.nextId(), "Title 1", "Desc 1", LocalDate.now().plusDays(1), testCreator);
        todoItemDAO.persist(item1);

        assertEquals(1, todoItemDAO.findAll().size());

        todoItemDAO.remove(item1.getId());
        assertEquals(0, todoItemDAO.findAll().size());
        assertNull(todoItemDAO.findById(item1.getId()));

        todoItemDAO.remove(999); // Should not throw exception
        assertEquals(0, todoItemDAO.findAll().size());
    }

    @Test
    void testPersistNullTodoItemThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoItemDAO.persist(null);
        });
        assertEquals("TodoItem cannot be null.", exception.getMessage());
    }

    @Test
    void testFindByTitleContainsNullThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoItemDAO.findByTitleContains(null);
        });
        assertEquals("Title cannot be null.", exception.getMessage());
    }

    @Test
    void testFindByDeadlineBeforeNullThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoItemDAO.findByDeadlineBefore(null);
        });
        assertEquals("Date cannot be null.", exception.getMessage());
    }

    @Test
    void testFindByDeadlineAfterNullThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoItemDAO.findByDeadlineAfter(null);
        });
        assertEquals("Date cannot be null.", exception.getMessage());
    }
}