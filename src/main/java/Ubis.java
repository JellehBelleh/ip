import java.util.Scanner;

public class Ubis {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final TaskList taskList = new TaskList();

    public static void main(String[] args) {
        welcome();
    }

    // Welcomes the user with print messages, then awaits commands
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

        // Keep handling commands. Exits when user inputs "bye"
        while (true) {
            handleInput(receiveInput());
        }
    }

    private static void printDashLine() {
        System.out.println("_".repeat(30));
    }

    // Handle exit of the bot.
    // 1. Prints goodbye
    // 2. Cleans up resources
    private static void exit() {
        System.out.println("Goodbye. See you soon!");
        printDashLine();
        SCANNER.close();
        System.exit(0);
    }

    // Method to get a line of input from the user.
    private static String receiveInput() {
        String command = SCANNER.nextLine();
        printDashLine();
        return command;
    }

    // Handles the incoming input
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
            default:
                taskList.addTask(new String[] { input }, TaskList.TaskType.TODO);
        }
        printDashLine();
    }
}
