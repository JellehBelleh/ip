import java.util.Arrays;
import java.util.Scanner;

/**
 * Entry point of Ubis chatbot, containing the overall logic flow
 */
public class Ubis {
    private TaskList taskList = new TaskList();
    private Parser parser;

    public static void main(String[] args) {
        Ubis ubis = new Ubis();
        ubis.welcome();
    }

    public Ubis() {
        this.parser = new Parser(this);
        this.taskList = Data.load();
    }

    /**
     * Entry point of the chatbot program.
     * Prints welcome banner and message, then handles
     * user input repeatedly until exit() is called.
     */
    private void welcome() {
        Ui.welcome();

        // Keep handling commands. Exits when user inputs "bye"
        while (true) {
            parser.handleInput(parser.receiveInput());
        }
    }

    /**
     * Return this Ubis's task list
     * @return task list
     */
    public TaskList getTaskList() {
        return this.taskList;
    }

    /**
     * Cleans up resources and terminates the Chatbot program,
     * printing a goodbye message as well.
     */
    public void exit() {
        Ui.printMessage(Ui.Message.GOODBYE);
        parser.cleanup();
        System.exit(0);
    }
}
