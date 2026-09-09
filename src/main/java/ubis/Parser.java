package ubis;

import java.util.Scanner;

/**
 * Parses user input from the console and dispatches the corresponding commands.
 */
public class Parser {
    public static final String[] ILLEGAL_ARTIFACTS = {
        "{", "}"
    };
    private static final String SAVE_FAILURE_WARNING = "\nWarning: Your change is available for this session, "
            + "but it could not be saved to disk.";

    private final Scanner scanner;
    private final Ubis ubis;

    /**
     * Constructs a Parser associated with the given Ubis chatbot instance.
     *
     * @param ubis Chatbot instance to control.
     */
    public Parser(Ubis ubis) {
        this.ubis = ubis;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of input from the user and prints a divider line.
     *
     * @return User input command string.
     */
    public String receiveInput() {
        String command = scanner.nextLine();
        Ui.printDashLine();
        return command;
    }

    /**
     * Parses the user's input line, executes the requested command, and returns the response string.
     *
     * @param input Raw input string entered by the user.
     * @return Response string generated for the command.
     */
    public String handleInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Ui.Message.EMPTY_INPUT.getMessage();
        }

        if (containsIllegalArtifact(input)) {
            return Ui.Message.ILLEGAL_INPUT.getMessage();
        }

        String[] parts = input.trim().split("\\s+", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : null;
        return executeCommand(command, argument);
    }

    /**
     * Executes a parsed command and returns the corresponding response.
     *
     * @param command Command keyword.
     * @param argument Optional command argument.
     * @return Response string generated for the command.
     */
    private String executeCommand(String command, String argument) {
        switch (command) {
            case "bye":
                return executeWithoutArgument(command, argument, Ui.Message.GOODBYE.getMessage());
            case "list":
                return executeWithoutArgument(command, argument, ubis.getTaskList().listTasks());
            case "help":
                return executeWithoutArgument(command, argument, Ui.Message.HELP.getMessage());
            case "mark":
                return markTask(argument);
            case "unmark":
                return unmarkTask(argument);
            case "delete":
                return deleteTask(argument);
            case "todo":
                return addTask(new Todo(), argument,
                        "Missing task name, please do \"todo task-name\" instead.");
            case "deadline":
                return addTask(new Deadline(), argument,
                        "Missing or invalid arguments, "
                                + "please do \"deadline task-name /by YYYY-MM-DD\" instead.");
            case "event":
                return addTask(new Event(), argument,
                        "Missing or invalid arguments, "
                                + "please do \"event task-name /from YYYY-MM-DD /to YYYY-MM-DD\" instead.");
            case "find":
                return ubis.getTaskList().find(argument);
            default:
                return "Unknown command \"" + command + "\". Type \"help\" for commands!";
        }
    }

    /**
     * Executes a command that does not accept arguments after validating its format.
     *
     * @param command Command keyword used in the error response.
     * @param argument Unexpected argument, or null when none was supplied.
     * @param response Normal command response.
     * @return Normal response when no argument was supplied, or a format error otherwise.
     */
    private String executeWithoutArgument(String command, String argument, String response) {
        if (argument != null) {
            return "The \"" + command + "\" command does not accept any arguments.";
        }
        return response;
    }

    /**
     * Marks a task after validating its task number.
     *
     * @param argument Task number argument.
     * @return Response string generated for the command.
     */
    private String markTask(String argument) {
        if (argument == null) {
            return "Please add the task number you want to mark!\n"
                    + "Example: \"mark 4\" if you want to mark the fourth task.";
        }
        if (!argument.matches("[0-9]+")) {
            return getInvalidTaskNumberMessage(argument);
        }
        try {
            String response = ubis.getTaskList().markTask(Integer.parseInt(argument.trim()));
            return saveAndAppendWarning(response);
        } catch (NumberFormatException e) {
            return getInvalidTaskNumberMessage(argument);
        }
    }

    /**
     * Unmarks a task after validating its task number.
     *
     * @param argument Task number argument.
     * @return Response string generated for the command.
     */
    private String unmarkTask(String argument) {
        if (argument == null) {
            return "Please add the task number you want to unmark!\n"
                    + "Example: \"unmark 4\" if you want to unmark the fourth task.";
        }
        if (!argument.matches("[0-9]+")) {
            return getInvalidTaskNumberMessage(argument);
        }
        try {
            String response = ubis.getTaskList().unmarkTask(Integer.parseInt(argument.trim()));
            return saveAndAppendWarning(response);
        } catch (NumberFormatException e) {
            return getInvalidTaskNumberMessage(argument);
        }
    }

    /**
     * Deletes a task after validating its task number.
     *
     * @param argument Task number argument.
     * @return Response string generated for the command.
     */
    private String deleteTask(String argument) {
        if (argument == null) {
            return "Please add the task number you want to delete!\n"
                    + "Example: \"delete 4\" if you want to delete the fourth task.";
        }
        if (!argument.matches("[0-9]+")) {
            return getInvalidTaskNumberMessage(argument);
        }
        try {
            String response = ubis.getTaskList().removeTask(Integer.parseInt(argument.trim()));
            return saveAndAppendWarning(response);
        } catch (NumberFormatException e) {
            return getInvalidTaskNumberMessage(argument);
        }
    }

    /**
     * Builds the shared response for a malformed or out-of-range integer representation.
     *
     * @param argument Invalid task number argument.
     * @return Error response explaining the invalid value.
     */
    private String getInvalidTaskNumberMessage(String argument) {
        return "Invalid task number of: " + argument + "\nPlease enter one positive whole number.";
    }

    /**
     * Initialises and adds a task, saving the updated list when successful.
     *
     * @param task Task object used to initialise the requested task type.
     * @param argument Task creation argument.
     * @param invalidMessage Response returned when the argument is invalid.
     * @return Response string generated for the command.
     */
    private String addTask(Task task, String argument, String invalidMessage) {
        Task initialisedTask = task.initialise(argument);
        if (initialisedTask == null) {
            return invalidMessage;
        }

        String response = ubis.getTaskList().addTask(initialisedTask);
        return saveAndAppendWarning(response);
    }

    /**
     * Saves the current task list and adds a user-facing warning if persistence fails.
     *
     * @param response Successful in-memory operation response.
     * @return Original response, with a warning appended when the save fails.
     */
    private String saveAndAppendWarning(String response) {
        return Storage.save(ubis.getTaskList()) ? response : response + SAVE_FAILURE_WARNING;
    }

    /**
     * Checks if the input string contains characters reserved for data storage.
     *
     * @param input String to be checked.
     * @return True if it contains illegal characters, false otherwise.
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
     * Closes underlying scanner resources upon application exit.
     */
    public void cleanup() {
        scanner.close();
    }
}
