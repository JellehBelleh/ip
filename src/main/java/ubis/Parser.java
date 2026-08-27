package ubis;

import java.util.Scanner;

/**
 * Parses user input from the console and dispatches the corresponding commands.
 */
public class Parser {
    public static final String[] ILLEGAL_ARTIFACTS = {
        "{", "}"
    };

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

        String[] parts = input.trim().split(" ", 2);
        String command = null;
        String argument = null;

        if (parts.length > 0) {
            command = parts[0];
        }
        if (parts.length > 1) {
            argument = parts[1];
        }

        String response;
        switch (command) {
            case "bye":
                return Ui.Message.GOODBYE.getMessage();
            case "list":
                return ubis.getTaskList().listTasks();
            case "help":
                return Ui.Message.HELP.getMessage();
            case "mark":
                if (argument == null) {
                    return "Please add the task number you want to mark!\n"
                            + "Example: \"mark 4\" if you want to mark the fourth task.";
                }
                try {
                    response = ubis.getTaskList().markTask(Integer.parseInt(argument.trim()));
                } catch (NumberFormatException e) {
                    return "Invalid task number of: " + argument
                            + "\nPlease try again!";
                }
                break;
            case "unmark":
                if (argument == null) {
                    return "Please add the task number you want to unmark!\n"
                            + "Example: \"unmark 4\" if you want to unmark the fourth task.";
                }
                try {
                    response = ubis.getTaskList().unmarkTask(Integer.parseInt(argument.trim()));
                } catch (NumberFormatException e) {
                    return "Invalid task number of: " + argument
                            + "\nPlease try again!";
                }
                break;
            case "delete":
                if (argument == null) {
                    return "Please add the task number you want to delete!\n"
                            + "Example: \"delete 4\" if you want to delete the fourth task.";
                }
                try {
                    response = ubis.getTaskList().removeTask(Integer.parseInt(argument.trim()));
                } catch (NumberFormatException e) {
                    return "Invalid task number of: " + argument
                            + "\nPlease try again!";
                }
                break;
            case "todo":
                Task todo = new Todo().initialise(argument);
                if (todo == null) {
                    return "Missing task name, please do \"todo task-name\" instead.";
                }
                response = ubis.getTaskList().addTask(todo);
                break;
            case "deadline":
                Task deadline = new Deadline().initialise(argument);
                if (deadline == null) {
                    return "Missing or invalid arguments, "
                            + "please do \"deadline task-name /by YYYY-MM-DD\" instead.";
                }
                response = ubis.getTaskList().addTask(deadline);
                break;
            case "event":
                Task event = new Event().initialise(argument);
                if (event == null) {
                    return "Missing or invalid arguments, "
                            + "please do \"event task-name /from YYYY-MM-DD /to YYYY-MM-DD\" instead.";
                }
                response = ubis.getTaskList().addTask(event);
                break;
            case "find":
                return ubis.getTaskList().find(argument);
            default:
                return "Unknown command \"" + command + "\". Type \"help\" for commands!";
        }
        Storage.save(ubis.getTaskList());
        return response;
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

