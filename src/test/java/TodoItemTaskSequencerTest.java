
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TodoItemTaskSequencerTest {

    @BeforeEach
    void setUp() {
        TodoItemTaskSequencer.setCurrentId(0);
    }

    @Test
    void testNextIdIncrementsAndReturnsCorrectId() {
        assertEquals(1, TodoItemTaskSequencer.nextId());
        assertEquals(2, TodoItemTaskSequencer.nextId());
    }

    @Test
    void testGetCurrentIdReturnsCurrentValue() {
        TodoItemTaskSequencer.nextId(); // currentId becomes 1
        assertEquals(1, TodoItemTaskSequencer.getCurrentId());
        TodoItemTaskSequencer.nextId(); // currentId becomes 2
        assertEquals(2, TodoItemTaskSequencer.getCurrentId());
    }

    @Test
    void testSetCurrentIdSetsCorrectValue() {
        TodoItemTaskSequencer.setCurrentId(10);
        assertEquals(10, TodoItemTaskSequencer.getCurrentId());
        assertEquals(11, TodoItemTaskSequencer.nextId());
    }

    @Test
    void testSequencerResets() {
        TodoItemTaskSequencer.nextId();
        TodoItemTaskSequencer.nextId();
        TodoItemTaskSequencer.setCurrentId(0);
        assertEquals(1, TodoItemTaskSequencer.nextId());
    }
}