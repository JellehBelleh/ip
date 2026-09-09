package ubis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests the creation and parsing of tasks using variable argument segments.
 */
public class TaskTest {

    @Test
    public void initialise_todoSegments_returnsTodo() {
        Task task = Task.initialise("T", "0", "read book");
        assertNotNull(task);
        assertEquals("{T}{0}{read book}", task.stringify());
    }

    @Test
    public void initialise_deadlineSegments_returnsDeadline() {
        Task task = Task.initialise("D", "1", "submit assignment", "2026-09-01");
        assertNotNull(task);
        assertEquals("{D}{1}{submit assignment}{2026-09-01}", task.stringify());
    }

    @Test
    public void initialise_eventSegments_returnsEvent() {
        Task task = Task.initialise("E", "0", "hackathon", "2026-09-01", "2026-09-03");
        assertNotNull(task);
        assertEquals("{E}{0}{hackathon}{2026-09-01}{2026-09-03}", task.stringify());
    }

    @Test
    public void initialise_insufficientSegments_returnsNull() {
        assertNull(Task.initialise("T", "0"));
        assertNull(Task.initialise("D", "0", "deadline without date"));
        assertNull(Task.initialise("E", "0", "event without end date", "2026-09-01"));
    }

    @Test
    public void initialise_invalidSymbol_returnsNull() {
        assertNull(Task.initialise("X", "0", "unknown task"));
    }

    @Test
    public void initialise_invalidStatus_returnsNull() {
        assertNull(Task.initialise("T", "done", "read book"));
        assertNull(Task.initialise("T", "2", "read book"));
    }

    @Test
    public void initialise_blankName_returnsNull() {
        assertNull(Task.initialise("T", "0", "   "));
    }

    @Test
    public void initialise_unexpectedExtraFields_returnsNull() {
        assertNull(Task.initialise("T", "0", "read book", "unexpected"));
        assertNull(Task.initialise("D", "0", "submit", "2026-09-01", "unexpected"));
        assertNull(Task.initialise("E", "0", "trip", "2026-09-01", "2026-09-03", "unexpected"));
    }
}
