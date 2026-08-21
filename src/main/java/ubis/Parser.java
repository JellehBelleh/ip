package ubis;

import java.util.Scanner;

/**
 * Class to parse input from user
 */
public class Parser {
    public static final String[] ILLEGAL_ARTIFACTS = {
            "{", "}"
    };

    private final Scanner scanner;
    private final Ubis ubis;

    public Parser(Ubis ubis) {
        this.ubis = ubis;
        scanner = new Scanner(System.in);
    }

    /**
     * Waits for user input and returns it in a String. Prints
     * a dashed line after for separation
     * @return a string containing the user's input
     */
    public String receiveInput() {
        String command = scanner.nextLine();
        Ui.printDashLine();
        return command;
    }

    /**
     * Parses string as user input, handles the logic
     * @param input string input from user
     */
    public void handleInput(String input) {
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
                ubis.exit();
                break;
            case "list":
                ubis.getTaskList().listTasks();
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
                        ubis.getTaskList().markTask(Integer.parseInt(argument));
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
                        ubis.getTaskList().unmarkTask(Integer.parseInt(argument));
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
                        ubis.getTaskList().removeTask(Integer.parseInt(argument));
                    } catch (NumberFormatException e) {
                        Ui.printMessage("Invalid task number of: " + argument
                                + "\nPlease try again!");
                    }
                }
                break;
            case "todo":
                ubis.getTaskList().addTask(new Todo().initialise(argument));
                break;
            case "deadline":
                ubis.getTaskList().addTask(new Deadline().initialise(argument));
                break;
            case "event":
                ubis.getTaskList().addTask(new Event().initialise(argument));
                break;
            case "find":
                ubis.getTaskList().find(argument);
                break;
            default:
                Ui.printMessage("Unknown command" + "\"" + command + "\"" + ". Type \"help\" for commands!");
        }
        Storage.save(ubis.getTaskList());
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
     * Cleanup resources. Only to be called when exiting
     */
    public void cleanup() {
        scanner.close();
    }
}
