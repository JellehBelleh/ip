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
        assertEquals("Please provide a keyword to find.\nExample: find book", tasks.find("   \t"));
    }

    @Test
    public void find_noMatches_returnsSpecificMessage() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo().initialise("read book"));
        assertEquals("No matching tasks found.", tasks.find("exercise"));
    }

    @Test
    public void taskNumber_outOfBounds_returnsErrorMessage() {
        TaskList tasks = new TaskList();
        assertEquals("There is no task number 0. Enter a task number shown by \"list\".", tasks.markTask(0));
        assertEquals("There is no task number -1. Enter a task number shown by \"list\".",
                tasks.removeTask(-1));
        assertEquals("There is no task number 1. Enter a task number shown by \"list\".",
                tasks.unmarkTask(1));
    }
}
