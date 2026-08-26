package ubis;

/**
 * Represents the different types of tasks supported by the application and their associated symbols.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String symbol;

    TaskType(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the single-letter symbol representing this task type.
     *
     * @return Single-letter symbol for storage and display.
     */
    public String getSymbol() {
        return this.symbol;
    }
}

