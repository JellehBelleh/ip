import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class to represent a deadline task
 */
public class Deadline extends Task {
    private LocalDate deadline;

    @Override
    public Task initialise(String input) {
        if (input == null) {
            Ui.printMessage("Missing arguments, please do \"deadline task-name /by deadline-of-task\" instead.");
        } else {
            String[] arguments = input.split(" /by ");
            if (arguments.length < 2 || arguments[0] == null ||
                    arguments[1] == null || arguments[0].isEmpty() || arguments[1].isEmpty()) {
                Ui.printMessage("Missing arguments, please do \"deadline task-name /by YYYY-MM-DD\" instead.");
            } else {
                this.name = arguments[0];
                try {
                    this.deadline = LocalDate.parse(arguments[1]);
                } catch (DateTimeParseException e) {
                    Ui.printMessage("Invalid deadline format, please do \"deadline task-name /by YYYY-MM-DD\" instead. " );
                    return null;
                }
                this.type = TaskType.DEADLINE;
                return this;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadline.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    @Override
    public String stringify() {
        return super.stringify() + "{" + deadline + "}";
    }
}
