public class Event extends Task {
    private String from;
    private String to;

    @Override
    public Task initialise(String input) {
        if (input == null) {
            System.out.println("Missing arguments, please do \"event task-name /from from-time /to to-time\" instead.");
        } else {
            String[] arguments = input.split(" /from | /to ");
            if (arguments.length < 3 || arguments[0] == null ||
                    arguments[1] == null || arguments[2] == null
                    || arguments[0].isEmpty() || arguments[1].isEmpty() || arguments[2].isEmpty()) {
                System.out.println("Missing arguments, please do \"event task-name /from from-time /to to-time\" instead.");
            } else {
                this.name = arguments[0];
                this.from = arguments[1];
                this.to = arguments[2];
                this.type = TaskType.EVENT;
                return this;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String stringify() {
        return super.stringify() + "{" + from + "}" + "{" + to + "}";
    }
}
