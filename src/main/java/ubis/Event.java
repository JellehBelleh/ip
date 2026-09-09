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
        if (input == null || input.isBlank()) {
            setInitialisationError("Please provide an event name, start date, and end date.\n"
                    + "Example: event school camp /from 2026-10-01 /to 2026-10-03");
            return null;
        }

        String trimmedInput = input.trim();
        int fromParameterCount = countOccurrences(FROM_PARAMETER_PATTERN, trimmedInput);
        int toParameterCount = countOccurrences(TO_PARAMETER_PATTERN, trimmedInput);
        if (fromParameterCount != 1) {
            setInitialisationError("An event must contain exactly one \"/from\" parameter.");
            return null;
        }
        if (toParameterCount != 1) {
            setInitialisationError("An event must contain exactly one \"/to\" parameter.");
            return null;
        }

        Matcher fromParameter = FROM_PARAMETER_PATTERN.matcher(trimmedInput);
        Matcher toParameter = TO_PARAMETER_PATTERN.matcher(trimmedInput);
        fromParameter.find();
        toParameter.find();
        if (fromParameter.start() > toParameter.start()) {
            setInitialisationError("The \"/from\" parameter must appear before \"/to\".");
            return null;
        }

        String taskName = trimmedInput.substring(0, fromParameter.start()).trim();
        String fromDateText = trimmedInput.substring(fromParameter.end(), toParameter.start()).trim();
        String toDateText = trimmedInput.substring(toParameter.end()).trim();
        if (taskName.isBlank()) {
            setInitialisationError("Please provide an event name before \"/from\".");
            return null;
        }
        if (fromDateText.isBlank()) {
            setInitialisationError("Please provide a start date after \"/from\" in YYYY-MM-DD format.");
            return null;
        }
        if (toDateText.isBlank()) {
            setInitialisationError("Please provide an end date after \"/to\" in YYYY-MM-DD format.");
            return null;
        }

        this.name = taskName;
        try {
            this.from = LocalDate.parse(fromDateText);
        } catch (DateTimeParseException e) {
            setInitialisationError("The event start date is invalid. "
                    + "Use YYYY-MM-DD and enter a real calendar date.");
            return null;
        }
        try {
            this.to = LocalDate.parse(toDateText);
        } catch (DateTimeParseException e) {
            setInitialisationError("The event end date is invalid. "
                    + "Use YYYY-MM-DD and enter a real calendar date.");
            return null;
        }

        if (!from.isBefore(to)) {
            setInitialisationError("The event start date must be earlier than the end date.");
            return null;
        }

        this.type = TaskType.EVENT;
        return this;
    }

    /**
     * Counts occurrences of a standalone command parameter in the input.
     *
     * @param parameterPattern Pattern matching the standalone parameter.
     * @param input Event arguments to inspect.
     * @return Number of standalone parameter occurrences.
     */
    private static int countOccurrences(Pattern parameterPattern, String input) {
        Matcher matcher = parameterPattern.matcher(input);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
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
