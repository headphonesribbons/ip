/**
 * A task that must be completed by a specified date or time.
 */
public class Deadline extends Task {
    /** Date or time by which the task should be completed. */
    protected String by;

    /** Creates an incomplete deadline task. */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String getDisplayDescription() {
        return description + " (by: " + by + ")";
    }
}
