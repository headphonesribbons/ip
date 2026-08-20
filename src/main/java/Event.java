/**
 * A task with a specified start and end date or time.
 */
public class Event extends Task {
    /** Date or time when the event starts. */
    protected String from;

    /** Date or time when the event ends. */
    protected String to;

    /**
     * Creates an incomplete event task.
     *
     * @param description text describing the event.
     * @param from date or time when the event starts.
     * @param to date or time when the event ends.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Formats this event for display in a task list.
     *
     * @return type icon, completion icon, description, and event range.
     */
    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + getDisplayDescription();
    }

    /** Returns the description together with the event range. */
    @Override
    public String getDisplayDescription() {
        return description + " (from: " + from + " to: " + to + ")";
    }

    /** Returns the event start text. */
    public String getFrom() {
        return from;
    }

    /** Returns the event end text. */
    public String getTo() {
        return to;
    }

    /** Returns the event type icon. */
    @Override
    public String getTypeIcon() {
        return "E";
    }
}
