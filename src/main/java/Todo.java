public class Todo extends Task{
    @Override
    public Task initialise(String input) {
        if (input == null || input.isEmpty()) {
            System.out.println("Missing task name, please do \"todo task-name\" instead.");
        } else {
            this.name = input;
            this.type = TaskType.TODO;
            return this;
        }
        return null;
    }
}
