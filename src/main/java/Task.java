/**
 * Represents one task in Computa's in-memory task list.
 */
public class Task {
    /** The text entered by the user for this task. */
    protected String description;

    /**
     * Creates a task with the given description.
     *
     * @param description text describing the task
     */
    public Task(String description) {
        this.description = description;
    }

    /**
     * Returns the task description.
     *
     * @return the description entered by the user
     */
    public String getDescription() {
        return description;
    }

    /**
     * Formats this task for display.
     *
     * @return the task description
     */
    @Override
    public String toString() {
        return description;
    }
}
