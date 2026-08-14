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
            handleCommand(receiveCommand());
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
    private static String receiveCommand() {
        String command = SCANNER.nextLine();
        printDashLine();
        return command;
    }

    // Handles the incoming command
    // For now, just echo input besides "bye" which exits
    private static void handleCommand(String command) {
        switch (command) {
            case "bye":
                exit();
                break;
            case "list":
                taskList.listTasks();
                break;
            default:
                taskList.addTask(command);
        }
        printDashLine();
    }
}
