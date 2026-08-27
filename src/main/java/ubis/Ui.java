package ubis;

import java.util.Arrays;

/**
 * Handles interactions with the user by displaying messages and banners to the console.
 */
public class Ui {
    private static final int LINE_LENGTH = 30;

    /**
     * Predefined system messages displayed to the user.
     */
    public enum Message {
        GOODBYE("Goodbye. See you soon!"),
        EMPTY_INPUT("Hi! You can type in a task name and I will keep track of it for you!\n"
                + "type \"help\" for information on commands."),
        ILLEGAL_INPUT("Sorry! Please ensure input does not contain any of the following characters: \n"
                + Arrays.toString(Parser.ILLEGAL_ARTIFACTS)),
        HELP("Here are some commands:\n"
                + "   list - list all tasks\n"
                + "   mark n - mark the nth task as done\n"
                + "   unmark n - mark the nth task as NOT done\n"
                + "   todo task-name - add a task\n"
                + "   deadline task-name /by task-deadline - add a deadline\n"
                + "   event task-name /from start /to end - add an event\n"
                + "   delete n - delete the nth task\n"
                + "   bye - exit the program");

        private final String message;

        Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }

    /**
     * Returns the introductory welcome message for the chatbot.
     *
     * @return Welcome message string.
     */
    public static String getWelcomeMessage() {
        return "Hello! I am Ubis.\nWhat can I do for you?";
    }

    /**
     * Prints the welcome banner and introductory message to the console.
     */
    public static void welcome() {
        String banner = " _   _ ____ ___ ____  \n"
                + "| | | | __ )|_ _/ ___| \n"
                + "| | | |  _ \\ | |\\___ \\ \n"
                + "| |_| | |_) || | ___) |\n"
                + " \\___/|____/|___|____/ \n";

        printDashLine();
        System.out.println(banner);
        System.out.println(getWelcomeMessage());
        printDashLine();
    }

    /**
     * Prints a divider line of dashes to the console.
     */
    public static void printDashLine() {
        System.out.println("_".repeat(LINE_LENGTH));
    }

    /**
     * Prints a predefined message followed by a divider line.
     *
     * @param message Predefined Message enum value to display.
     */
    public static void printMessage(Message message) {
        System.out.println(message.getMessage());
        printDashLine();
    }

    /**
     * Prints a string message followed by a divider line.
     *
     * @param message Text message to be printed.
     */
    public static void printMessage(String message) {
        System.out.println(message);
        printDashLine();
    }
}

