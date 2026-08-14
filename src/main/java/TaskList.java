import java.util.ArrayList;
import java.util.List;

// Class that can register and track tasks
public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(String taskName) {
        tasks.add(new Task(taskName));
        System.out.println("added: " + taskName);
    }

    public void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ": " + tasks.get(i));
        }
    }
}
