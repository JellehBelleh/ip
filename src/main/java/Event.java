import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class to represent an event task
 */
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    @Override
    public Task initialise(String input) {
        if (input == null) {
            System.out.println("Missing arguments, please do \"event task-name /from from-time /to to-time\" instead.");
        } else {
            String[] arguments = input.split(" /from | /to ");
            if (arguments.length < 3 || arguments[0] == null ||
                    arguments[1] == null || arguments[2] == null
                    || arguments[0].isEmpty() || arguments[1].isEmpty() || arguments[2].isEmpty()) {
                System.out.println("Missing arguments, please do \"event task-name /from from-time /to to-time\" instead.");
            } else {
                this.name = arguments[0];
                try {
                    this.from = LocalDate.parse(arguments[1]);
                    this.to = LocalDate.parse(arguments[2]);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid event format, please do \"event task-name /from YYYY-MM-DD /to YYYY-MM-DD\" instead.");
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
        return super.toString() + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    @Override
    public String stringify() {
        return super.stringify() + "{" + from + "}" + "{" + to + "}";
    }
}
