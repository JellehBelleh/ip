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
     * Adds a task to the task list and returns a confirmation message.
     *
     * @param task Task to be added.
     * @return Confirmation message of the added task.
     */
    public String addTask(Task task) {
        if (task == null) {
            return "";
        }
        tasks.add(task);
        return "added: " + task;
    }

    /**
     * Adds the given task to the task list with an option to suppress console output.
     *
     * @param task Task to be added.
     * @param shouldPrint Kept for backward compatibility.
     */
    public void addTask(Task task, boolean shouldPrint) {
        addTask(task);
    }

    /**
     * Removes a task from the list using its 1-based number and returns a status message.
     *
     * @param taskNumber 1-based number of the task to remove.
     * @return Status message indicating success or failure.
     */
    public String removeTask(int taskNumber) {
        // Class invariant: the internal tasks list should always be initialized
        assert tasks != null : "Tasks list should not be null";

        if (taskNumber < 1 || taskNumber > tasks.size()) {
            return "Sorry, there is no task number " + taskNumber + ". Please try again.";
        }

        int index = taskNumber - 1;

        String taskDescription = tasks.get(index).toString();
        tasks.remove(index);
        return "Okay, I've deleted " + taskDescription;
    }

    /**
     * Returns a formatted string listing all tasks currently stored.
     *
     * @return String representation of all tasks.
     */
    public String listTasks() {
        return listTasks(tasks);
    }

    /**
     * Formats the provided list of tasks into a numbered string.
     *
     * @param tasksToDisplay List of tasks to format.
     * @return Formatted string of tasks.
     */
    private String listTasks(List<Task> tasksToDisplay) {
        if (tasksToDisplay.isEmpty()) {
            return "No tasks to show";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasksToDisplay.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            Task task = tasksToDisplay.get(i);
            // Invariant: all tasks stored in the list must be non-null
            assert task != null : "Task in tasksToDisplay should not be null";
            sb.append(i + 1).append(": ").append(task);
        }
        return sb.toString();
    }

    /**
     * Marks a task as done using its 1-based number and returns a status message.
     *
     * @param taskNumber 1-based number of the task to mark.
     * @return Confirmation or error message.
     */
    public String markTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            return "Sorry, there is no task number " + taskNumber + ". Please try again.";
        }

        Task task = tasks.get(taskNumber - 1);
        // Invariant: task retrieved from a valid index within bounds must not be null
        assert task != null : "Task to mark should not be null";
        task.mark();
        return "Nice! I've marked this task as DONE:\n  " + task;
    }

    /**
     * Marks a task as undone using its 1-based number and returns a status message.
     *
     * @param taskNumber 1-based number of the task to unmark.
     * @return Confirmation or error message.
     */
    public String unmarkTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            return "Sorry, there is no task number " + taskNumber + ". Please try again.";
        }

        Task task = tasks.get(taskNumber - 1);
        // Invariant: task retrieved from a valid index within bounds must not be null
        assert task != null : "Task to unmark should not be null";
        task.unmark();
        return "Okay, I've marked this task NOT done yet:\n  " + task;
    }

    /**
     * Finds and lists all tasks whose names contain the given keyword.
     *
     * @param keyword String keyword to search for in task names.
     * @return Formatted list of matching tasks.
     */
    public String find(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return "Missing parameter for \"find\", do \"find name\" instead.";
        }

        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            // Invariant: all tasks stored in the list must be non-null
            assert task != null : "Task in tasks should not be null";
            if (task.getName().contains(keyword)) {
                matchingTasks.add(task);
            }
        }

        return listTasks(matchingTasks);
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

