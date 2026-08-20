import java.util.Scanner;

/**
 * Entry point of Ubis chatbot, containing the overall logic flow
 */
public class Ubis {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static TaskList taskList = new TaskList();

    public static void main(String[] args) {
        welcome();
    }

    /**
     * Entry point of the chatbot program.
     * Prints welcome banner and message, then handles
     * user input repeatedly until exit() is called.
     */
    private static void welcome() {
        String banner = " _   _ ____ ___ ____  \n"
                + "| | | | __ )|_ _/ ___| \n"
                + "| | | |  _ \\ | |\\___ \\ \n"
                + "| |_| | |_) || | ___) |\n"
                + " \\___/|____/|___|____/ \n";

        printDashLine();
        System.out.println(banner);
        System.out.println("Hello! I am Ubis.");
        System.out.println("What can I do for you?");
        printDashLine();

        taskList = Data.load();

        // Keep handling commands. Exits when user inputs "bye"
        while (true) {
            handleInput(receiveInput());
        }
    }

    /**
     * Prints a line of length 30 on the console
     */
    private static void printDashLine() {
        System.out.println("_".repeat(30));
    }

    /**
     * Cleans up resources and terminates the Chatbot program,
     * printing a goodbye message as well.
     */
    private static void exit() {
        System.out.println("Goodbye. See you soon!");
        printDashLine();
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
        printDashLine();
        return command;
    }

    /**
     * Handles incoming user input, performing the appropriate tasks
     * @param input string input from the user
     */
    private static void handleInput(String input) {
        if (input.isEmpty()) {
            System.out.println("Hi! You can type in a task name and I will keep track of it for you!");
            System.out.println("type \"help\" for information on commands.");

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
                System.out.println("Here are some commands: ");
                System.out.println("  list - list all tasks");
                System.out.println("  mark n - mark the nth task as done");
                System.out.println("  unmark n - mark the nth task as NOT done");
                System.out.println("  todo task-name - add a task");
                System.out.println("  deadline task-name /by task-deadline - add a deadline");
                System.out.println("  event task-name /from start /to end - add an event");
                System.out.println("  delete n - delete the nth task");
                System.out.println("  bye - exit the program");
                break;
            case "mark":
                if (argument == null) {
                    System.out.println("Please add the task number you want to mark!");
                    System.out.println("Example: \"mark 4\" if you want to mark the fourth task.");
                } else {
                    try {
                        taskList.markTask(Integer.parseInt(argument));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task number of: " + argument);
                        System.out.println("Please try again!");
                    }
                }
                break;
            case "unmark":
                if (argument == null) {
                    System.out.println("Please add the task number you want to unmark!");
                    System.out.println("Example: \"unmark 4\" if you want to unmark the fourth task.");
                } else {
                    try {
                        taskList.unmarkTask(Integer.parseInt(argument));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task number of: " + argument);
                        System.out.println("Please try again!");
                    }
                }
                break;
            case "delete":
                if (argument == null) {
                    System.out.println("Please add the task number you want to delete!");
                    System.out.println("Example: \"delete 4\" if you want to delete the fourth task.");
                } else {
                    try {
                        taskList.removeTask(Integer.parseInt(argument));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task number of: " + argument);
                        System.out.println("Please try again!");
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
                System.out.println("Unknown command. Type \"help\" for commands!");
        }
        Data.save(taskList);
        printDashLine();
    }
}
