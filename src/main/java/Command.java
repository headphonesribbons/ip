import java.util.ArrayList;

/** Represents one user command that can be executed by Computa. */
public abstract class Command {
    /** Executes this command using the current application collaborators. */
    public abstract void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws ComputaException;

    /** Returns whether this command should end the command loop. */
    public boolean isExit() {
        return false;
    }
}
