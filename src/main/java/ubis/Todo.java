package ubis;

/**
 * Represents a todo task without any date or time constraints.
 */
public class Todo extends Task {

    /**
     * Initialises the todo task with the given description.
     *
     * @param input Task description.
     * @return Initialised Todo task, or null if description is invalid.
     */
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

