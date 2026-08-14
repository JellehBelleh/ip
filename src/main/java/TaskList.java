import java.util.ArrayList;
import java.util.List;

// Class that can register and track tasks
public class TaskList {
    private List<Task> tasks;
    public static enum TaskType { TODO, DEADLINE, EVENT };

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(String[] args, TaskType type) {
        switch (type) {
            case TaskType.TODO:
                tasks.add(new Todo(args[0]));
                break;
            case TaskType.DEADLINE:
                tasks.add(new Deadline(args[0], args[1]));
                break;
            case TaskType.EVENT:
                tasks.add(new Event(args[0], args[1], args[2]));
                break;
        }
        System.out.println("added: " + args[0]);
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
