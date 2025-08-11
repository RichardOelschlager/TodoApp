import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TodoItemIdSequencerTest {

    @BeforeEach
    void setUp() {
        TodoItemIdSequencer.setCurrentId(0);
    }

    @Test
    void testNextIdIncrementsAndReturnsCorrectId() {
        assertEquals(1, TodoItemIdSequencer.nextId());
        assertEquals(2, TodoItemIdSequencer.nextId());
    }

    @Test
    void testGetCurrentIdReturnsCurrentValue() {
        TodoItemIdSequencer.nextId(); // currentId becomes 1
        assertEquals(1, TodoItemIdSequencer.getCurrentId());
        TodoItemIdSequencer.nextId(); // currentId becomes 2
        assertEquals(2, TodoItemIdSequencer.getCurrentId());
    }

    @Test
    void testSetCurrentIdSetsCorrectValue() {
        TodoItemIdSequencer.setCurrentId(10);
        assertEquals(10, TodoItemIdSequencer.getCurrentId());
        assertEquals(11, TodoItemIdSequencer.nextId());
    }

    @Test
    void testSequencerResets() {
        TodoItemIdSequencer.nextId();
        TodoItemIdSequencer.nextId();
        TodoItemIdSequencer.setCurrentId(0);
        assertEquals(1, TodoItemIdSequencer.nextId());
    }
}
