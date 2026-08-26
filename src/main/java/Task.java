import java.time.LocalDate;

/**
 * Represents one task in Computa's in-memory task list.
 */
public class Task {
    /** The text entered by the user for this task. */
    protected String description;

    /** Whether this task has been completed. */
    protected boolean isDone;

    /**
     * Returns this task in the format used by the task data file.
     *
     * @return status and description fields separated by vertical bars.
     */
    public String toFileFormat() {
        return " | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description text describing the task.
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
     * Returns the status icon used when displaying this task.
     *
     * @return {@code X} for a completed task, or a space otherwise.
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the task description.
     *
     * @return the description entered by the user.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the type icon for this task.
     *
     * @return the one-letter icon used in task lists.
     */
    public String getTypeIcon() {
        return "T";
    }

    /**
     * Returns the description and any date/time details for display outside a list.
     *
     * @return the task's display description.
     */
    public String getDisplayDescription() {
        return description;
    }

    /**
     * Checks whether this task occurs on a date. ToDos have no date, so they never match.
     *
     * @param date date to check.
     * @return false for a task without date information.
     */
    public boolean occursOn(LocalDate date) {
        return false;
    }

    /**
     * Formats this task for display in a task list.
     *
     * @return type icon, completion icon, and description.
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + getDisplayDescription();
    }
}
