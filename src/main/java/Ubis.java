public class Ubis {
    public static void main(String[] args) {
        welcome();
        exit();
    }

    // Welcomes the user with print messages
    private static void welcome() {
        String banner = " _   _ ____ ___ ____  \n"
                + "| | | | __ )|_ _/ ___| \n"
                + "| | | |  _ \\ | |\\___ \\ \n"
                + "| |_| | |_) || | ___) |\n"
                + " \\___/|____/|___|____/ \n";

        System.out.println("_".repeat(30));
        System.out.println(banner);
        System.out.println("Hello! I am Ubis.");
        System.out.println("What can I do for you?");
        System.out.println("_".repeat(30));
    }

    // Handle exit of the bot, for now just print a message
    private static void exit() {
        System.out.println("Goodbye. See you soon!");
    }
}
