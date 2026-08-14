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

    public void markTask(int index) {
        if (index < 1 || index > tasks.size()) {
            System.out.println("Sorry, there is no task number " + index + ". Please try again.");
            return;
        }

        Task task = tasks.get(index - 1);
        task.mark();
        System.out.println("Nice! I've marked this task as DONE:");
        System.out.println("  " + task);
    }

    public void unmarkTask(int index) {
        if (index < 1 || index > tasks.size()) {
            System.out.println("Sorry, there is no task number " + index + ". Please try again.");
            return;
        }

        Task task = tasks.get(index - 1);
        task.unmark();
        System.out.println("Okay, I've marked this task NOT done yet:");
        System.out.println("  " + task);
    }
}
