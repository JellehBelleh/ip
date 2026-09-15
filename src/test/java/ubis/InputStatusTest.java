package ubis;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests the acceptance status used to retain invalid GUI commands for correction.
 */
public class InputStatusTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    public void getResponse_invalidCommands_reportsFailureAfterSuccess() {
        Ubis ubis = new Ubis(temporaryDirectory.resolve("tasks.txt"));
        String[] invalidCommands = {"unknown", "help extra", "todo", "deadline report /by bad",
            "event trip /from 2026-10-03 /to 2026-10-01", "find", "mark", "unmark text",
            "delete 99999999999999", "mark 0", "delete 8", "unmark 8", "todo {bad}"};
        for (String command : invalidCommands) {
            ubis.getResponse("list");
            assertTrue(ubis.wasInputSuccessful());
            ubis.getResponse(command);
            assertFalse(ubis.wasInputSuccessful(), command);
        }
    }

    @Test
    public void getResponse_validCommands_reportsSuccess() {
        Ubis ubis = new Ubis(temporaryDirectory.resolve("tasks.txt"));
        String[] commands = {"todo read", "mark 1", "unmark 1", "find absent", "delete 1", "help", "bye"};
        for (String command : commands) {
            ubis.getResponse(command);
            assertTrue(ubis.wasInputSuccessful(), command);
        }
    }

    @Test
    public void getResponse_saveFailure_stillAcceptsInMemoryChange() {
        Ubis ubis = new Ubis(temporaryDirectory);
        ubis.getResponse("todo read");
        assertTrue(ubis.wasInputSuccessful());
    }
}
