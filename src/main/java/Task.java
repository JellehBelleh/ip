// Class that represents a task
public abstract class Task {
    private String name;
    private boolean done;
    private String symbol;

    public Task(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.done = false;
    }

    public void mark() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
    }

    @Override
    public String toString() {
        return "[" + symbol + "]" + "[" + (this.done ? "X" : " ") + "] " + this.name;
    }
}
