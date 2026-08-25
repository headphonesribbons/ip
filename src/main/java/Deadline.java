/**
 * A task that must be completed by a specified date or time.
 */
public class Deadline extends Task {
    /** Date or time by which the task should be completed. */
    protected String by;

    /**
     * Creates an incomplete deadline task.
     *
     * @param description text describing the task.
     * @param by date or time by which the task should be completed.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toFileFormat() {
        return "D" + super.toFileFormat() + " | " + this.by;
    }

    /**
     * Formats this deadline for display in a task list.
     *
     * @return type icon, completion icon, description, and deadline.
     */
    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + getDisplayDescription();
    }

    /** Returns the description together with the deadline. */
    @Override
    public String getDisplayDescription() {
        return description + " (by: " + by + ")";
    }

    /** Returns the deadline text. */
    public String getBy() {
        return by;
    }

    /** Returns the deadline type icon. */
    @Override
    public String getTypeIcon() {
        return "D";
    }
}
