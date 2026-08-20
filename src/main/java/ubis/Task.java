package ubis;

import java.util.Arrays;

/**
 * Abstract class that represents a task
 */
public abstract class Task {
    protected String name;
    protected boolean done;
    protected TaskType type;

    public Task() {
        this.name = null;
        this.done = false;
        this.type = null;
    }

    /**
     * Marks the task as done
     */
    public void mark() {
        this.done = true;
    }

    /**
     * Marks the task as undone
     */
    public void unmark() {
        this.done = false;
    }

    /**
     * Initialises the task given the input string. Definition is specific to task type.
     * @param input string of arguments
     * @return the initialised task
     */
    public abstract Task initialise(String input);

    /**
     * Initialises the task given the arguments as a string array.
     * @param segments input arguments
     * @return the initialised task
     */
    public static Task initialise(String[] segments) {
        if (segments.length < 3) {
            System.out.println("Invalid String[]: " + Arrays.toString(segments));
            return null;
        }

        String symbol = segments[0];
        boolean done = segments[1].equals("1");
        String name = segments[2];
        Task task = null;

        switch (symbol) {
            case "T":
                task = new Todo();
                task.initialise(name);
                task.done = done;
                break;
            case "D":
                if (segments.length < 4) {
                    System.out.println("Not enough arguments for deadline: " + Arrays.toString(segments));
                    break;
                }
                task = new Deadline();
                task.initialise(name + " /by " + segments[3]);
                task.done = done;
                break;
            case "E":
                if (segments.length < 5) {
                    System.out.println("Not enough arguments for event: " + Arrays.toString(segments));
                    break;
                }
                task = new Event();
                task.initialise(name + " /from " + segments[3] + " /to " + segments[4]);
                task.done = done;
                break;
            default:
                System.out.println("Invalid case: " + symbol);
                break;
        }

        return task;
    }

    /**
     * Stringify the task into a format for storage. If you want
     * to display the task, use toString() instead.
     * @return string of the task to be stored
     */
    public String stringify() {
        return "{" + this.type.getSymbol()+ "}" + "{" + (this.done ? "1" : "0") + "}" + "{" + this.name + "}";
    }

    @Override
    public String toString() {
        return "[" + this.type.getSymbol() + "]" + "[" + (this.done ? "X" : " ") + "] " + this.name;
    }
}
