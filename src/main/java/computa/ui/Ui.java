package computa.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import computa.task.Task;
import computa.util.DateTimeParser;

/** Handles all text displayed by Computa. */
public class Ui {
    /** Separates the greeting and each command response in the console output. */
    private static final String SEPARATOR = "____________________________________________________________";

    /** Prints Computa's greeting. */
    public void showGreeting() {
        System.out.println(SEPARATOR);
        System.out.println("                         COMPUTA");
        System.out.println("Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°");
        System.out.println("I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)");
        System.out.println("What can I do for you?");
        showSeparator();
    }

    /** Prints the separator between command responses. */
    public void showSeparator() {
        System.out.println(SEPARATOR);
    }

    /** Prints Computa's farewell. */
    public void showFarewell() {
        System.out.println("Noooo don't go!!! Hmph. Fine... Hope to see you again soon!");
    }

    /** Prints all tasks and their completion status. */
    public void showTasks(ArrayList<Task> tasks) {
        System.out.println("We've got so much to do (⋟﹏⋞)");
        System.out.println("Hmph! I guess I'll have to spend more time with you "
                + "(⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /** Prints tasks whose descriptions contain the supplied keyword, ignoring case. */
    public void showTasksContaining(ArrayList<Task> tasks, String keyword) {
        System.out.println("Goshujin-Sama, can't you do this yourself?");
        System.out.println("Hmph. I guess I have no choice.");

        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);
        int matchingTaskCount = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String description = task.getDescription().toLowerCase(Locale.ROOT);
            if (description.contains(normalizedKeyword)) {
                matchingTaskCount++;
                System.out.println((i + 1) + "." + task);
            }
        }
        if (matchingTaskCount == 0) {
            System.out.println("No matching tasks found.");
        }
    }

    /** Prints deadlines and events that occur on the requested date. */
    public void showTasksOnDate(ArrayList<Task> tasks, LocalDate date) {
        System.out.println("Tasks on " + DateTimeParser.formatDateForDisplay(date) + ":");
        int matchingTaskNumber = 0;
        for (Task task : tasks) {
            if (task.occursOn(date)) {
                matchingTaskNumber++;
                System.out.println(matchingTaskNumber + "." + task);
            }
        }
        if (matchingTaskNumber == 0) {
            System.out.println("No deadlines or events found.");
        }
    }

    /** Prints the acknowledgement shown after a task is added. */
    public void showAddedTask(Task task, int taskCount) {
        System.out.println("More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list. (⋟﹏⋞)");
        System.out.println("(.づ◡﹏◡)づ. When will we get some alone time together?");
    }

    /** Prints the acknowledgement shown after marking or unmarking a task. */
    public void showStatusUpdate(Task task, boolean isMark) {
        if (isMark) {
            System.out.println("Yatta! (ᗒᗨᗕ) I knew you could do it (✧ᴗ✧✿) \n");
        } else {
            System.out.println("Gambare, Goshujin-Sama ! ˚‧º·( 。ᗒ ‸ ◕✿) \n");
        }
        System.out.println("  [" + task.getStatusIcon() + "] "
                + task.getDisplayDescription());
    }

    /** Prints the acknowledgement shown after deleting a task. */
    public void showDeletedTask(Task deletedTask, int taskCount) {
        System.out.println("Goshujin-Sama, you don't want to do this with me anymore? "
                + "(๑˃̣̣̥⌓˂̣̣̥)");
        System.out.println("  " + deletedTask);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println("Not that I want to hang out with you anyway. (๑•́ ₃ •̀๑)");
    }

    /** Prints a user-input error without terminating the session. */
    public void showError(String message) {
        System.out.println(message);
    }
}
