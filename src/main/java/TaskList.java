import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Class that can register and track tasks
public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        if (task == null) {
            return;
        }
        tasks.add(task);
        System.out.println("added: " + task);
    }

    public void addTask(String[] strings) {
        if (strings.length < 3) {
            System.out.println("Invalid String[]: " + Arrays.toString(strings));
            return;
        }

        String symbol = strings[0];
        boolean done = strings[1].equals("1");
        String name = strings[2];

    }

    public void removeTask(int num) {
        if (num < 1 || num > tasks.size()) {
            System.out.println("Sorry, there is no task number " + num + ". Please try again.");
            return;
        }

        int index = num - 1;
        String taskDescription = tasks.get(index).toString();
        tasks.remove(index);
        System.out.println("Okay, I've deleted " + taskDescription);
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Task t : tasks) {
            result.append(t.stringify()).append("\n");
        }

        return result.toString();
    }
}
