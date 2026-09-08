package computa.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Consumer;

import computa.task.Task;
import computa.util.DateTimeParser;

/** Handles all text displayed by Computa. */
public class Ui {
    /** Separates the greeting and each command response in the console output. */
    private static final String SEPARATOR = "____________________________________________________________";
    /** Receives each line produced by the chatbot. */
    private final Consumer<String> output;

    /** Creates a UI that writes output to the console. */
    public Ui() {
        this(System.out::println);
    }

    /**
     * Creates a UI that sends output to the supplied receiver.
     *
     * @param output receiver for each complete line of output.
     */
    public Ui(Consumer<String> output) {
        this.output = output;
    }

    /** Sends one line to the configured output receiver. */
    private void printLine(String line) {
        output.accept(line);
    }

    /** Prints Computa's greeting. */
    public void showGreeting() {
        printLine(SEPARATOR);
        printLine("                         COMPUTA");
        printLine("Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°");
        printLine("I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)");
        printLine("What can I do for you?");
        showSeparator();
    }

    /** Prints the separator between command responses. */
    public void showSeparator() {
        printLine(SEPARATOR);
    }

    /** Prints Computa's farewell. */
    public void showFarewell() {
        printLine("Noooo don't go!!! Hmph. Fine... Hope to see you again soon!");
    }

    /** Prints all tasks and their completion status. */
    public void showTasks(ArrayList<Task> tasks) {
        printLine("We've got so much to do (⋟﹏⋞)");
        printLine("Hmph! I guess I'll have to spend more time with you "
                + "(⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)");
        for (int i = 0; i < tasks.size(); i++) {
            printLine((i + 1) + "." + tasks.get(i));
        }
    }

    /** Prints tasks whose descriptions contain the supplied keyword, ignoring case. */
    public void showTasksContaining(ArrayList<Task> tasks, String keyword) {
        printLine("Goshujin-Sama, can't you do this yourself?");
        printLine("Hmph. I guess I have no choice.");

        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);
        int matchingTaskCount = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String description = task.getDescription().toLowerCase(Locale.ROOT);
            if (description.contains(normalizedKeyword)) {
                matchingTaskCount++;
                printLine((i + 1) + "." + task);
            }
        }
        if (matchingTaskCount == 0) {
            printLine("No matching tasks found.");
        }
    }

    /** Prints deadlines and events that occur on the requested date. */
    public void showTasksOnDate(ArrayList<Task> tasks, LocalDate date) {
        printLine("Tasks on " + DateTimeParser.formatDateForDisplay(date) + ":");
        int matchingTaskNumber = 0;
        for (Task task : tasks) {
            if (task.occursOn(date)) {
                matchingTaskNumber++;
                printLine(matchingTaskNumber + "." + task);
            }
        }
        if (matchingTaskNumber == 0) {
            printLine("No deadlines or events found.");
        }
    }

    /** Prints the acknowledgement shown after a task is added. */
    public void showAddedTask(Task task, int taskCount) {
        printLine("More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७");
        printLine("  " + task);
        printLine("Now you have " + taskCount + " tasks in the list. (⋟﹏⋞)");
        printLine("(.づ◡﹏◡)づ. When will we get some alone time together?");
    }

    /** Prints the acknowledgement shown after marking or unmarking a task. */
    public void showStatusUpdate(Task task, boolean isMark) {
        if (isMark) {
            printLine("Yatta! (ᗒᗨᗕ) I knew you could do it (✧ᴗ✧✿) \n");
        } else {
            printLine("Gambare, Goshujin-Sama ! ˚‧º·( 。ᗒ ‸ ◕✿) \n");
        }
        printLine("  [" + task.getStatusIcon() + "] "
                + task.getDisplayDescription());
    }

    /** Prints the acknowledgement shown after deleting a task. */
    public void showDeletedTask(Task deletedTask, int taskCount) {
        printLine("Goshujin-Sama, you don't want to do this with me anymore? "
                + "(๑˃̣̣̥⌓˂̣̣̥)");
        printLine("  " + deletedTask);
        printLine("Now you have " + taskCount + " tasks in the list.");
        printLine("Not that I want to hang out with you anyway. (๑•́ ₃ •̀๑)");
    }

    /** Prints a user-input error without terminating the session. */
    public void showError(String message) {
        printLine(message);
    }
}
