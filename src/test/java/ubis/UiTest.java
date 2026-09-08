package ubis;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests messages displayed by the user interface.
 */
public class UiTest {

    @Test
    public void helpMessage_containsAllCommandSyntaxes() {
        String helpMessage = Ui.Message.HELP.getMessage();
        String[] commandSyntaxes = {
            "todo <task name>",
            "deadline <task name> /by <YYYY-MM-DD>",
            "event <task name> /from <YYYY-MM-DD> /to <YYYY-MM-DD>",
            "list",
            "find <keyword>",
            "mark <task number>",
            "unmark <task number>",
            "delete <task number>",
            "help",
            "bye"
        };

        for (String commandSyntax : commandSyntaxes) {
            assertTrue(helpMessage.contains("  " + commandSyntax + "\n"),
                    "Help message is missing command syntax: " + commandSyntax);
        }
    }
}
