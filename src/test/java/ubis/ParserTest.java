package ubis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

/**
 * Tests command-level format validation and application-boundary error handling.
 */
public class ParserTest {
    @Test
    public void handleInput_generalWhitespace_parsesCommand() {
        Ubis ubis = new Ubis(new TaskList());
        assertEquals(Ui.Message.HELP.getMessage(), ubis.getResponse("  \t help \t "));
    }

    @Test
    public void handleInput_argumentForArgumentlessCommand_returnsError() {
        Ubis ubis = new Ubis(new TaskList());
        assertEquals("The \"list\" command does not accept any arguments.",
                ubis.getResponse("list unexpected"));
        assertEquals("The \"help\" command does not accept any arguments.",
                ubis.getResponse("help me"));
        assertEquals("The \"bye\" command does not accept any arguments.",
                ubis.getResponse("bye now"));
    }

    @Test
    public void handleInput_malformedTaskNumbers_returnsError() {
        Ubis ubis = new Ubis(new TaskList());
        String[] invalidCommands = {
            "mark -1", "mark 1 2", "unmark 1.5", "delete +1", "delete task"
        };

        for (String command : invalidCommands) {
            assertTrue(ubis.getResponse(command).contains("Please enter one positive whole number."));
        }
        assertEquals("That task number is too large. Please enter a task number shown by \"list\".",
                ubis.getResponse("mark 999999999999999999999999"));
    }

    @Test
    public void handleInput_invalidTodo_returnsSpecificMessage() {
        Ubis ubis = new Ubis(new TaskList());
        assertEquals("Please provide a name for the todo.\nExample: todo read a book",
                ubis.getResponse("todo"));
    }

    @Test
    public void handleInput_invalidDeadline_returnsSpecificMessage() {
        Ubis ubis = new Ubis(new TaskList());

        assertTrue(ubis.getResponse("deadline report").contains("needs a \"/by\" parameter"));
        assertEquals("Please provide a task name before \"/by\".",
                ubis.getResponse("deadline /by 2026-09-30"));
        assertTrue(ubis.getResponse("deadline report /by").contains("provide a deadline date"));
        assertTrue(ubis.getResponse("deadline report /by 2026-02-30").contains("date is invalid"));
        assertTrue(ubis.getResponse(
                "deadline report /by 2026-09-29 /by 2026-09-30").contains("exactly one \"/by\""));
    }

    @Test
    public void handleInput_invalidEventParameters_returnsSpecificMessage() {
        Ubis ubis = new Ubis(new TaskList());

        assertTrue(ubis.getResponse("event camp").contains("exactly one \"/from\""));
        assertTrue(ubis.getResponse("event camp /from 2026-09-01").contains("exactly one \"/to\""));
        assertEquals("The \"/from\" parameter must appear before \"/to\".",
                ubis.getResponse("event camp /to 2026-09-03 /from 2026-09-01"));
        assertTrue(ubis.getResponse(
                "event camp /from 2026-09-01 /from 2026-09-02 /to 2026-09-03")
                .contains("exactly one \"/from\""));
        assertTrue(ubis.getResponse("event /from 2026-09-01 /to 2026-09-03")
                .contains("provide an event name"));
    }

    @Test
    public void handleInput_invalidEventDates_returnsSpecificMessage() {
        Ubis ubis = new Ubis(new TaskList());

        assertTrue(ubis.getResponse("event camp /from bad-date /to 2026-09-03")
                .contains("start date is invalid"));
        assertTrue(ubis.getResponse("event camp /from 2026-09-01 /to bad-date")
                .contains("end date is invalid"));
        assertEquals("The event start date must be earlier than the end date.",
                ubis.getResponse("event camp /from 2026-09-03 /to 2026-09-01"));
        assertEquals("The event start date must be earlier than the end date.",
                ubis.getResponse("event camp /from 2026-09-03 /to 2026-09-03"));
    }

    @Test
    public void receiveInput_endOfStream_returnsNull() {
        Scanner emptyScanner = new Scanner(new ByteArrayInputStream(new byte[0]));
        Parser parser = new Parser(new Ubis(new TaskList()), emptyScanner);
        assertNull(parser.receiveInput());
        parser.cleanup();
    }

    @Test
    public void getResponse_unexpectedRuntimeFailure_returnsFriendlyMessage() {
        TaskList failingTaskList = new TaskList() {
            @Override
            public String listTasks() {
                throw new IllegalStateException("Deliberate test failure");
            }
        };
        Ubis ubis = new Ubis(failingTaskList);

        assertEquals(Ui.Message.UNEXPECTED_ERROR.getMessage(), ubis.getResponse("list"));
    }
}
