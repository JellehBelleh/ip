import java.util.Scanner;

public class Ubis {
    public static void main(String[] args) {
        welcome();
    }

    // Welcomes the user with print messages
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

    // Handle exit of the bot, for now just print a message
    private static void exit() {
        System.out.println("Goodbye. See you soon!");
        printDashLine();
        System.exit(0);
    }

    // Method to get a line of input from the user.
    private static String receiveCommand() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        printDashLine();
        return command;
    }

    // Handles the incoming command
    // For now, just echo input besides "bye" which exits
    private static void handleCommand(String command) {
        if (command.equals("bye")) {
            exit();
        } else {
            System.out.println("Ubis: " + command);
            printDashLine();
        }
    }
}
