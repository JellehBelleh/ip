package ubis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start date and an end date.
 */
public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");

    private LocalDate from;
    private LocalDate to;

    /**
     * Initialises the event task from user input containing description, start date, and end date.
     *
     * @param input Input string containing description, "/from &lt;start&gt;", and "/to &lt;end&gt;".
     * @return Initialised Event task, or null if arguments/dates are invalid.
     */
    @Override
    public Task initialise(String input) {
        if (input == null) {
            Ui.printMessage("Missing arguments, please do \"event task-name /from from-time /to to-time\" instead.");
        } else {
            String[] arguments = input.split(" /from | /to ");
            if (arguments.length < 3
                    || arguments[0] == null
                    || arguments[1] == null
                    || arguments[2] == null
                    || arguments[0].isEmpty()
                    || arguments[1].isEmpty()
                    || arguments[2].isEmpty()) {
                Ui.printMessage("Missing arguments, please do \"event task-name /from from-time /to to-time\" instead.");
            } else {
                this.name = arguments[0];
                try {
                    this.from = LocalDate.parse(arguments[1]);
                    this.to = LocalDate.parse(arguments[2]);
                } catch (DateTimeParseException e) {
                    Ui.printMessage("Invalid event format, please do \"event task-name /from YYYY-MM-DD /to YYYY-MM-DD\" instead.");
                    return null;
                }
                this.type = TaskType.EVENT;
                return this;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from.format(OUTPUT_FORMATTER)
                + " to: " + to.format(OUTPUT_FORMATTER) + ")";
    }

    @Override
    public String stringify() {
        return super.stringify() + "{" + from + "}" + "{" + to + "}";
    }
}

