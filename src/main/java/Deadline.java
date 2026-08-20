/**
 * Class to represent a deadline task
 */
public class Deadline extends Task {
    private String deadline;

    @Override
    public Task initialise(String input) {
        if (input == null) {
            System.out.println("Missing arguments, please do \"deadline task-name /by deadline-of-task\" instead.");
        } else {
            String[] arguments = input.split(" /by ");
            if (arguments.length < 2 || arguments[0] == null ||
                    arguments[1] == null || arguments[0].isEmpty() || arguments[1].isEmpty()) {
                System.out.println("Missing arguments, please do \"deadline task-name /by deadline-of-task\" instead.");
            } else {
                this.name = arguments[0];
                this.deadline = arguments[1];
                this.type = TaskType.DEADLINE;
                return this;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadline + ")";
    }

    @Override
    public String stringify() {
        return super.stringify() + "{" + deadline + "}";
    }
}
