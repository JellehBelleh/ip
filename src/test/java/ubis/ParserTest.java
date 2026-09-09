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
            "mark -1", "mark 1 2", "unmark 1.5", "delete +1", "delete task",
            "mark 999999999999999999999999"
        };

        for (String command : invalidCommands) {
            assertTrue(ubis.getResponse(command).contains("Please enter one positive whole number."));
        }
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
