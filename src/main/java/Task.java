// Class that represents a task
public abstract class Task {
    protected String name;
    protected boolean done;
    protected TaskType type;

    public Task() {
        this.name = null;
        this.done = false;
        this.type = null;
    }

    public void mark() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
    }

    public abstract Task initialise(String input);

    public String stringify() {
        return "{" + this.type.getSymbol()+ "}" + "{" + (this.done ? "1" : "0") + "}" + "{" + this.name + "}";
    }

    @Override
    public String toString() {
        return "[" + this.type.getSymbol() + "]" + "[" + (this.done ? "X" : " ") + "] " + this.name;
    }
}
