package ubis;

import java.util.Arrays;

/**
 * Class to handle UI of the chatbot
 */
public class Ui {
    public enum Message {
        GOODBYE("Goodbye. See you soon!"),
        EMPTY_INPUT("Hi! You can type in a task name and I will keep track of it for you!\n" +
                            "type \"help\" for information on commands."),
        ILLEGAL_INPUT("Sorry! Please ensure input does not contain any of the following characters: \n"
                + Arrays.toString(Parser.ILLEGAL_ARTIFACTS)),
        HELP("Here are some commands:\n" +
                "   list - list all tasks\n" +
                "   mark n - mark the nth task as done\n" +
                "   unmark n - mark the nth task as NOT done\n" +
                "   todo task-name - add a task\n" +
                "   deadline task-name /by task-deadline - add a deadline\n" +
                "   event task-name /from start /to end - add an event\n" +
                "   delete n - delete the nth task\n" +
                "   bye - exit the program");

        private final String message;

        Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }

    /**
     * Prints welcome message for the user
     */
    public static void welcome() {
        String banner = " _   _ ____ ___ ____  \n"
                + "| | | | __ )|_ _/ ___| \n"
                + "| | | |  _ \\ | |\\___ \\ \n"
                + "| |_| | |_) || | ___) |\n"
                + " \\___/|____/|___|____/ \n";

        printDashLine();
        System.out.println(banner);
        System.out.println("Hello! I am ubis.Ubis.");
        System.out.println("What can I do for you?");
        printDashLine();
    }

    /**
     * Prints a line of length 30 on the console
     */
    public static void printDashLine() {
        System.out.println("_".repeat(30));
    }

    /**
     * Prints a message set by the Message object
     * @param msg Message object you want to display
     */
    public static void printMessage(Message msg) {
        System.out.println(msg.getMessage());
        printDashLine();
    }

    /**
     * Prints a string as a message
     * @param msg string to be printed
     */
    public static void printMessage(String msg) {
        System.out.println(msg);
        printDashLine();
    }
}
