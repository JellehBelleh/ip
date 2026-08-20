package ubis;

/**
 * class to represent a todo task
 */
public class Todo extends Task {
    @Override
    public Task initialise(String input) {
        if (input == null || input.isEmpty()) {
            Ui.printMessage("Missing task name, please do \"todo task-name\" instead.");
        } else {
            this.name = input;
            this.type = TaskType.TODO;
            return this;
        }
        return null;
    }
}
