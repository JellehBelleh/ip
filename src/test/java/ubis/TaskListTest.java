package ubis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests task-list validation at its public operation boundaries.
 */
public class TaskListTest {
    @Test
    public void find_blankKeyword_returnsMissingParameterMessage() {
        TaskList tasks = new TaskList();
        assertEquals("Missing parameter for \"find\", do \"find name\" instead.", tasks.find("   \t"));
    }

    @Test
    public void taskNumber_outOfBounds_returnsErrorMessage() {
        TaskList tasks = new TaskList();
        assertEquals("Sorry, there is no task number 0. Please try again.", tasks.markTask(0));
        assertEquals("Sorry, there is no task number -1. Please try again.", tasks.removeTask(-1));
        assertEquals("Sorry, there is no task number 1. Please try again.", tasks.unmarkTask(1));
    }
}
