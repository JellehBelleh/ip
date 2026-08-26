package ubis;

/**
 * Entry point of the Ubis chatbot application, coordinating storage, user interface, and command parsing.
 */
public class Ubis {
    private TaskList taskList;
    private Parser parser;

    /**
     * Constructs a new Ubis chatbot instance and loads saved tasks from storage.
     */
    public Ubis() {
        this.parser = new Parser(this);
        this.taskList = Storage.load();
    }

    /**
     * Main entry point for the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Ubis ubis = new Ubis();
        ubis.welcome();
    }

    /**
     * Displays the welcome banner and repeatedly processes user commands until exit.
     */
    private void welcome() {
        Ui.welcome();

        // Keep handling commands. Exits when user inputs "bye"
        while (true) {
            parser.handleInput(parser.receiveInput());
        }
    }

    /**
     * Returns the chatbot's task list.
     *
     * @return Current task list.
     */
    public TaskList getTaskList() {
        return this.taskList;
    }

    /**
     * Cleans up resources, prints a goodbye message, and terminates the application.
     */
    public void exit() {
        Ui.printMessage(Ui.Message.GOODBYE);
        parser.cleanup();
        System.exit(0);
    }
}

