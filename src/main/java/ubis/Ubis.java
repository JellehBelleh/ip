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
     * Generates a response string for a given user input.
     *
     * @param input Raw user input string.
     * @return Chatbot response string.
     */
    public String getResponse(String input) {
        return parser.handleInput(input);
    }

    /**
     * Displays the welcome banner and repeatedly processes user commands until exit.
     */
    private void welcome() {
        Ui.welcome();

        // Keep handling commands. Exits when user inputs "bye"
        while (true) {
            String input = parser.receiveInput();
            String response = getResponse(input);
            Ui.printMessage(response);
            if ("bye".equalsIgnoreCase(input.trim())) {
                exit();
            }
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

