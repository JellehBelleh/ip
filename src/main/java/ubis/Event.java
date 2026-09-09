package ubis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an event task with a start date and an end date.
 */
public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final Pattern ARGUMENT_PATTERN = Pattern.compile(
            "^(.+?)\\s+/from\\s+(\\S+)\\s+/to\\s+(\\S+)$");
    private static final Pattern FROM_PARAMETER_PATTERN = Pattern.compile("(?<!\\S)/from(?!\\S)");
    private static final Pattern TO_PARAMETER_PATTERN = Pattern.compile("(?<!\\S)/to(?!\\S)");

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
            return null;
        }

        String trimmedInput = input.trim();
        Matcher arguments = ARGUMENT_PATTERN.matcher(trimmedInput);
        if (!occursExactlyOnce(FROM_PARAMETER_PATTERN, trimmedInput)
                || !occursExactlyOnce(TO_PARAMETER_PATTERN, trimmedInput)
                || !arguments.matches()
                || arguments.group(1).isBlank()) {
            Ui.printMessage("Missing arguments, "
                    + "please do \"event task-name /from from-time /to to-time\" instead.");
            return null;
        }

        this.name = arguments.group(1).trim();
        try {
            this.from = LocalDate.parse(arguments.group(2));
            this.to = LocalDate.parse(arguments.group(3));
        } catch (DateTimeParseException e) {
            Ui.printMessage("Invalid event format, "
                    + "please do \"event task-name /from YYYY-MM-DD /to YYYY-MM-DD\" instead.");
            return null;
        }

        if (!from.isBefore(to)) {
            Ui.printMessage("Invalid event period: the start date must be before the end date.");
            return null;
        }

        this.type = TaskType.EVENT;
        return this;
    }

    /**
     * Checks that a standalone command parameter occurs exactly once in the input.
     *
     * @param parameterPattern Pattern matching the standalone parameter.
     * @param input Event arguments to inspect.
     * @return True if the parameter occurs exactly once.
     */
    private static boolean occursExactlyOnce(Pattern parameterPattern, String input) {
        Matcher matcher = parameterPattern.matcher(input);
        return matcher.find() && !matcher.find();
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
