// Class that represents a task
public abstract class Task {
    protected String name;
    protected boolean done;
    protected String symbol;

    public Task() {
        this.name = null;
        this.done = false;
        this.symbol = null;
    }

    public void mark() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
    }

    public abstract Task initialise(String input);

    public String stringify() {
        return "{" + this.symbol + "}" + "{" + (this.done ? "1" : "0") + "}" + "{" + this.name + "}";
    }

    @Override
    public String toString() {
        return "[" + symbol + "]" + "[" + (this.done ? "X" : " ") + "] " + this.name;
    }
}
