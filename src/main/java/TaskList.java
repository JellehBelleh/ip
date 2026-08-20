import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that manages a list of tasks
 */
public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Add a task to the task list. Will print outcome by default.
     * Use addTask(task, false) to add task silently
     * @param task task to be added
     */
    public void addTask(Task task) {
        addTask(task, true);
    }

    /**
     * Add the given task to the task list.
     * @param task task to be added
     * @param print whether to print outcome of operation
     */
    public void addTask(Task task, boolean print) {
        if (task == null) {
            return;
        }
        tasks.add(task);
        if (print) {
            System.out.println("added: " + task);
        }
    }

    /**
     * Removes task from the list
     * @param num Index of task to remove. Note that this input is 1-indexed.
     */
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

    /**
     * Prints all tasks in the list. No side effects.
     */
    public void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ": " + tasks.get(i));
        }
    }

    /**
     * marks a task as done in the list, using the index
     * @param index index of task (1-indexed)
     */
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
