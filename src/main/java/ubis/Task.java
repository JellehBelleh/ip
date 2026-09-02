package ubis;

import java.util.Arrays;

/**
 * Represents an abstract task with a name, completion status, and task type.
 */
public abstract class Task {
    private static final String COMPLETED_STATUS = "1";
    private static final String INCOMPLETE_STATUS = "0";

    protected String name;
    protected boolean isDone;
    protected TaskType type;

    /**
     * Constructs a default Task instance.
     */
    public Task() {
        this.name = null;
        this.isDone = false;
        this.type = null;
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as undone.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Initialises the task given the input argument string. Specific to each task type.
     *
     * @param input String of arguments for task creation.
     * @return The initialised task, or null if input was invalid.
     */
    public abstract Task initialise(String input);

    /**
     * Initialises the task given the arguments parsed from storage or test inputs.
     *
     * @param segments Input argument segments.
     * @return The initialised task, or null if arguments are invalid.
     */
    public static Task initialise(String... segments) {
        if (segments.length < 3) {
            System.out.println("Invalid String[]: " + Arrays.toString(segments));
            return null;
        }

        TaskType taskType = TaskType.fromSymbol(segments[0]);
        if (taskType == null) {
            System.out.println("Invalid case: " + segments[0]);
            return null;
        }

        boolean isDone = segments[1].equals(COMPLETED_STATUS);
        Task task;
        switch (taskType) {
            case TODO:
                task = initialiseTodo(segments[2]);
                break;
            case DEADLINE:
                task = initialiseDeadline(segments);
                break;
            case EVENT:
                task = initialiseEvent(segments);
                break;
            default:
                return null;
        }

        if (task != null) {
            task.isDone = isDone;
        }
        return task;
    }

    /**
     * Creates a todo task from storage data.
     *
     * @param name Stored todo task name.
     * @return Initialised todo task.
     */
    private static Task initialiseTodo(String name) {
        Task task = new Todo();
        task.initialise(name);
        return task;
    }

    /**
     * Creates a deadline task from storage data.
     *
     * @param segments Stored deadline task segments.
     * @return Initialised deadline task, or null when its date is missing.
     */
    private static Task initialiseDeadline(String[] segments) {
        if (segments.length < 4) {
            System.out.println("Not enough arguments for deadline: " + Arrays.toString(segments));
            return null;
        }

        Task task = new Deadline();
        task.initialise(segments[2] + " /by " + segments[3]);
        return task;
    }

    /**
     * Creates an event task from storage data.
     *
     * @param segments Stored event task segments.
     * @return Initialised event task, or null when a date is missing.
     */
    private static Task initialiseEvent(String[] segments) {
        if (segments.length < 5) {
            System.out.println("Not enough arguments for event: " + Arrays.toString(segments));
            return null;
        }

        Task task = new Event();
        task.initialise(segments[2] + " /from " + segments[3] + " /to " + segments[4]);
        return task;
    }

    /**
     * Formats the task into a string representation suitable for file storage.
     *
     * @return String of the task formatted for storage.
     */
    public String stringify() {
        String status = this.isDone ? COMPLETED_STATUS : INCOMPLETE_STATUS;
        return "{" + this.type.getSymbol() + "}" + "{" + status + "}" + "{" + this.name + "}";
    }

    @Override
    public String toString() {
        return "[" + this.type.getSymbol() + "]" + "[" + (this.isDone ? "X" : " ") + "] " + this.name;
    }

    /**
     * Returns the name/description of the task.
     *
     * @return Name of the task.
     */
    public String getName() {
        return this.name;
    }
}
