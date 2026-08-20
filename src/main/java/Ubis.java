import java.util.Arrays;
import java.util.Scanner;

/**
 * Entry point of Ubis chatbot, containing the overall logic flow
 */
public class Ubis {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static TaskList taskList = new TaskList();

    // Items not allowed in user input as they can affect parsing
    public static final String[] ILLEGAL_ARTIFACTS = {
            "{", "}"
    };

    public static void main(String[] args) {
        welcome();
    }

    /**
     * Entry point of the chatbot program.
     * Prints welcome banner and message, then handles
     * user input repeatedly until exit() is called.
     */
    private static void welcome() {
        Ui.welcome();

        taskList = Data.load();

        // Keep handling commands. Exits when user inputs "bye"
        while (true) {
            handleInput(receiveInput());
        }
    }



    /**
     * Cleans up resources and terminates the Chatbot program,
     * printing a goodbye message as well.
     */
    private static void exit() {
        Ui.printMessage(Ui.Message.GOODBYE);
        SCANNER.close();
        System.exit(0);
    }

    /**
     * Waits for user input and returns it in a String. Prints
     * a dashed line after for separation
     * @return a string containing the user's input
     */
    private static String receiveInput() {
        String command = SCANNER.nextLine();
        Ui.printDashLine();
        return command;
    }

    /**
     * Helps to check if the given input has illegal artifacts
     * that could affect parsing of data
     * @param input to be checked
     * @return true if it contains illegal artifacts, false otherwise
     */
    private static boolean containsIllegalArtifact(String input) {
        for (String illegal : ILLEGAL_ARTIFACTS) {
            if (input.contains(illegal)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Handles incoming user input, performing the appropriate tasks
     * @param input string input from the user
     */
    private static void handleInput(String input) {
        if (input.isEmpty()) {
            Ui.printMessage(Ui.Message.EMPTY_INPUT);
            return;
        }

        if (containsIllegalArtifact(input)) {
            Ui.printMessage(Ui.Message.ILLEGAL_INPUT);
            return;
        }

        String[] parts = input.split(" ", 2);
        String command = null;
        String argument = null;

        if (parts.length > 0) {
            command = parts[0];
        }
        if (parts.length > 1) {
            argument = parts[1];
        }

        switch (command) {
            case "bye":
                exit();
                break;
            case "list":
                taskList.listTasks();
                break;
            case "help":
                Ui.printMessage(Ui.Message.HELP);
                break;
            case "mark":
                if (argument == null) {
                    Ui.printMessage("Please add the task number you want to mark!\n"
                    + "Example: \"mark 4\" if you want to mark the fourth task.");
                } else {
                    try {
                        taskList.markTask(Integer.parseInt(argument));
                    } catch (NumberFormatException e) {
                        Ui.printMessage("Invalid task number of: " + argument
                        + "\nPlease try again!");
                    }
                }
                break;
            case "unmark":
                if (argument == null) {
                    Ui.printMessage("Please add the task number you want to unmark!"
                    + "\nExample: \"unmark 4\" if you want to unmark the fourth task.");
                } else {
                    try {
                        taskList.unmarkTask(Integer.parseInt(argument));
                    } catch (NumberFormatException e) {
                        Ui.printMessage("Invalid task number of: " + argument
                        + "\nPlease try again!");
                    }
                }
                break;
            case "delete":
                if (argument == null) {
                    Ui.printMessage("Please add the task number you want to delete!"
                    + "\nExample: \"delete 4\" if you want to delete the fourth task.");
                } else {
                    try {
                        taskList.removeTask(Integer.parseInt(argument));
                    } catch (NumberFormatException e) {
                        Ui.printMessage("Invalid task number of: " + argument
                        + "\nPlease try again!");
                    }
                }
                break;
            case "todo":
                taskList.addTask(new Todo().initialise(argument));
                break;
            case "deadline":
                taskList.addTask(new Deadline().initialise(argument));
                break;
            case "event":
                taskList.addTask(new Event().initialise(argument));
                break;
            default:
                Ui.printMessage("Unknown command. Type \"help\" for commands!");
        }
        Data.save(taskList);
    }
}
