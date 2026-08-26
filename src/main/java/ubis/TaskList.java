package ubis;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages an in-memory list of tasks and supports operations such as adding, deleting, marking, and finding tasks.
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the task list and prints a confirmation message.
     *
     * @param task Task to be added.
     */
    public void addTask(Task task) {
        addTask(task, true);
    }

    /**
     * Adds the given task to the task list with an option to suppress console output.
     *
     * @param task Task to be added.
     * @param shouldPrint Whether to print the outcome of the operation.
     */
    public void addTask(Task task, boolean shouldPrint) {
        if (task == null) {
            return;
        }
        tasks.add(task);
        if (shouldPrint) {
            System.out.println("added: " + task);
        }
    }

    /**
     * Removes a task from the list using its 1-based number.
     *
     * @param taskNumber 1-based number of the task to remove.
     */
    public void removeTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Sorry, there is no task number " + taskNumber + ". Please try again.");
            return;
        }

        int index = taskNumber - 1;
        String taskDescription = tasks.get(index).toString();
        tasks.remove(index);
        System.out.println("Okay, I've deleted " + taskDescription);
    }

    /**
     * Prints all tasks currently stored in the list.
     */
    public void listTasks() {
        listTasks(tasks);
    }

    /**
     * Prints all tasks in the provided list.
     *
     * @param tasksToDisplay List of tasks to be printed.
     */
    private void listTasks(List<Task> tasksToDisplay) {
        for (int i = 0; i < tasksToDisplay.size(); i++) {
            System.out.println((i + 1) + ": " + tasksToDisplay.get(i));
        }
        if (tasksToDisplay.isEmpty()) {
            System.out.println("No tasks to show");
        }
        Ui.printDashLine();
    }

    /**
     * Marks a task as done in the list using its 1-based number.
     *
     * @param taskNumber 1-based number of the task to mark.
     */
    public void markTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Sorry, there is no task number " + taskNumber + ". Please try again.");
            return;
        }

        Task task = tasks.get(taskNumber - 1);
        task.mark();
        System.out.println("Nice! I've marked this task as DONE:");
        System.out.println("  " + task);
    }

    /**
     * Marks a task as undone in the list using its 1-based number.
     *
     * @param taskNumber 1-based number of the task to unmark.
     */
    public void unmarkTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Sorry, there is no task number " + taskNumber + ". Please try again.");
            return;
        }

        Task task = tasks.get(taskNumber - 1);
        task.unmark();
        System.out.println("Okay, I've marked this task NOT done yet:");
        System.out.println("  " + task);
    }

    /**
     * Finds and lists all tasks whose names contain the given keyword.
     *
     * @param keyword String keyword to search for in task names.
     */
    public void find(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            Ui.printMessage("Missing parameter for \"find\", do \"find name\" instead.");
            return;
        }

        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getName().contains(keyword)) {
                matchingTasks.add(task);
            }
        }

        listTasks(matchingTasks);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Task task : tasks) {
            result.append(task.stringify()).append("\n");
        }

        return result.toString();
    }
}

