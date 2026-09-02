package ubis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that has a deadline date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");

    private LocalDate deadline;

    /**
     * Initialises the deadline task from user input containing description and date.
     *
     * @param input Input string containing description and "/by &lt;date&gt;".
     * @return Initialised Deadline task, or null if arguments/date are invalid.
     */
    @Override
    public Task initialise(String input) {
        if (input == null) {
            Ui.printMessage("Missing arguments, please do \"deadline task-name /by deadline-of-task\" instead.");
            return null;
        }

        String[] arguments = input.split(" /by ");
        if (arguments.length < 2 || arguments[0].isEmpty() || arguments[1].isEmpty()) {
            Ui.printMessage("Missing arguments, please do \"deadline task-name /by YYYY-MM-DD\" instead.");
            return null;
        }

        this.name = arguments[0];
        try {
            this.deadline = LocalDate.parse(arguments[1]);
        } catch (DateTimeParseException e) {
            Ui.printMessage("Invalid deadline format, "
                    + "please do \"deadline task-name /by YYYY-MM-DD\" instead.");
            return null;
        }

        this.type = TaskType.DEADLINE;
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadline.format(OUTPUT_FORMATTER) + ")";
    }

    @Override
    public String stringify() {
        return super.stringify() + "{" + deadline + "}";
    }
}
