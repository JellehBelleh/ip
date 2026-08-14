// Class that represents a task
public class Task {
    private String name;
    private boolean done;

    public Task(String name) {
        this.name = name;
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
        return "[" + (this.done ? "X" : " ") + "] " + this.name;
    }
}
