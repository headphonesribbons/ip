package computa;

/**
 * A task without a date or time attached to it.
 */
public class Todo extends Task {

    /**
     * Creates an incomplete todo task.
     *
     * @param description text describing the task
     */
    public Todo(String description) {
        super(description);
    }
}
