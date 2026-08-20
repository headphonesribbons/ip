/**
 * A task with a specified start and end date or time.
 */
public class Event extends Task {
    /** Date or time when the event starts. */
    protected String from;

    /** Date or time when the event ends. */
    protected String to;

    /** Creates an incomplete event task. */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String getDisplayDescription() {
        return description + " (from: " + from + " to: " + to + ")";
    }
}
