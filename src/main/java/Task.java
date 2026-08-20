/**
 * Represents one task in Computa's in-memory task list.
 */
public class Task {
    /** The text entered by the user for this task. */
    protected String description;

    /** Whether this task has been completed. */
    protected boolean isDone;

    /**
     * Creates a task with the given description.
     *
     * @param description text describing the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as completed. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks this task as incomplete. */
    public void markAsUndone() {
        isDone = false;
    }

    /**
     * Returns the completion icon used when displaying this task.
     *
     * @return {@code X} for a completed task, or a space otherwise
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the task description.
     *
     * @return the description entered by the user
     */
    public String getDescription() {
        return description;
    }

    /** Returns the one-letter icon for this task type. */
    public String getTypeIcon() {
        return "T";
    }

    /** Returns the description and any date/time details for this task. */
    public String getDisplayDescription() {
        return description;
    }

    /**
     * Formats this task for display.
     *
     * @return the task description
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + getDisplayDescription();
    }
}
