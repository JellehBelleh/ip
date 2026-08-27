package ubis;

import java.util.Arrays;

/**
 * Represents an abstract task with a name, completion status, and task type.
 */
public abstract class Task {
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
     * Initialises the task given the arguments as a string array from storage.
     *
     * @param segments Input arguments parsed from storage.
     * @return The initialised task, or null if arguments are invalid.
     */
    public static Task initialise(String[] segments) {
        if (segments.length < 3) {
            System.out.println("Invalid String[]: " + Arrays.toString(segments));
            return null;
        }

        String symbol = segments[0];
        boolean isDone = segments[1].equals("1");
        String name = segments[2];
        Task task = null;

        switch (symbol) {
            case "T":
                task = new Todo();
                task.initialise(name);
                task.isDone = isDone;
                break;
            case "D":
                if (segments.length < 4) {
                    System.out.println("Not enough arguments for deadline: " + Arrays.toString(segments));
                    break;
                }
                task = new Deadline();
                task.initialise(name + " /by " + segments[3]);
                task.isDone = isDone;
                break;
            case "E":
                if (segments.length < 5) {
                    System.out.println("Not enough arguments for event: " + Arrays.toString(segments));
                    break;
                }
                task = new Event();
                task.initialise(name + " /from " + segments[3] + " /to " + segments[4]);
                task.isDone = isDone;
                break;
            default:
                System.out.println("Invalid case: " + symbol);
                break;
        }

        return task;
    }

    /**
     * Formats the task into a string representation suitable for file storage.
     *
     * @return String of the task formatted for storage.
     */
    public String stringify() {
        return "{" + this.type.getSymbol() + "}" + "{" + (this.isDone ? "1" : "0") + "}" + "{" + this.name + "}";
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

