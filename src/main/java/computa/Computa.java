package computa;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A minimal chatbot that stores tasks and displays them when requested.
 */
public class Computa {
    /** Separates the greeting and each command response in the console output. */
    private static final String SEPARATOR = "____________________________________________________________";

    /**
     * Prints Computa's greeting, then processes commands until {@code bye} is entered.
     *
     * @param args command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(SEPARATOR);
        System.out.println("                         COMPUTA");
        System.out.println("Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°");
        System.out.println("I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(SEPARATOR);

            if (command.equals("bye")) {
                System.out.println("Noooo don't go!!! Hmph. Fine... Hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            }

            try {
                if (command.equals("list")) {
                    printTasks(tasks);
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    updateTaskStatus(command, tasks, true);
                } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                    updateTaskStatus(command, tasks, false);
                } else if (command.equals("delete") || command.startsWith("delete ")) {
                    deleteTask(command, tasks);
                } else {
                    Task newTask = createTask(command);
                    tasks.add(newTask);
                    printAddedTask(newTask, tasks.size());
                }
            } catch (ComputaException exception) {
                System.out.println(exception.getMessage());
            }

            System.out.println(SEPARATOR);
        }
    }

    /**
     * Prints all tasks and their completion status.
     *
     * @param tasks stored tasks.
     */
    private static void printTasks(ArrayList<Task> tasks) {
        System.out.println("We've got so much to do (⋟﹏⋞)");
        System.out.println("Hmph! I guess I'll have to spend more time with you "
                + "(⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Creates a task object from a todo, deadline, or event command.
     *
     * @param command complete command entered by the user.
     * @return the corresponding task.
     * @throws ComputaException if the command is malformed or unknown.
     */
    private static Task createTask(String command) throws ComputaException {
        if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.substring("todo".length()).trim();
            if (description.isEmpty()) {
                throw new ComputaException("Hmph! This is just an excuse to hang out with me, right?\n"
                        + "Who says you get to spend empty time with me? ୧( ˵ ° ~ ° ˵ )୨");
            }
            return new Todo(description);
        }

        if (command.equals("deadline") || command.startsWith("deadline ")) {
            String details = command.substring("deadline".length()).trim();
            int byIndex = details.indexOf("/by");
            if (byIndex < 0) {
                throw new ComputaException("A deadline needs a description and a /by date or time.");
            }
            String description = details.substring(0, byIndex).trim();
            String by = details.substring(byIndex + "/by".length()).trim();
            if (description.isEmpty() || by.isEmpty()) {
                throw new ComputaException("A deadline needs a description and a /by date or time.");
            }
            return new Deadline(description, by);
        }

        if (command.equals("event") || command.startsWith("event ")) {
            String details = command.substring("event".length()).trim();
            int fromIndex = details.indexOf("/from");
            int toIndex = details.indexOf("/to", fromIndex + 1);
            if (fromIndex < 0 || toIndex < 0) {
                throw new ComputaException("An event needs a description, /from date or time, and /to date or time.");
            }
            String description = details.substring(0, fromIndex).trim();
            String from = details.substring(fromIndex + "/from".length(), toIndex).trim();
            String to = details.substring(toIndex + "/to".length()).trim();
            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new ComputaException("An event needs a description, /from date or time, and /to date or time.");
            }
            return new Event(description, from, to);
        }

        throw new ComputaException("Hmph! Making small talk won't get you anywhere.  ʕ ꈍᴥꈍʔ");
    }

    /**
     * Prints the acknowledgement shown after a task is added.
     *
     * @param task newly added task.
     * @param taskCount number of tasks after adding the task.
     */
    private static void printAddedTask(Task task, int taskCount) {
        System.out.println("More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list. (⋟﹏⋞)");
        System.out.println("(.づ◡﹏◡)づ. When will we get some alone time together?");
    }

    /**
     * Marks or unmarks a task selected by its one-based number.
     *
     * @param command the complete mark or unmark command.
     * @param tasks stored tasks.
     * @param isMark true to mark the task, false to unmark it.
     */
    private static void updateTaskStatus(String command, ArrayList<Task> tasks, boolean isMark)
            throws ComputaException {
        String[] parts = command.trim().split("\\s+");
        if (parts.length != 2) {
            throw new ComputaException("Please provide a valid task number.");
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new ComputaException("Please provide a valid task number.");
            }

            int taskIndex = taskNumber - 1;
            Task task = tasks.get(taskIndex);
            if (isMark) {
                task.markAsDone();
                System.out.println("Yatta! (ᗒᗨᗕ) I knew you could do it (✧ᴗ✧✿) \n");
            } else {
                task.markAsUndone();
                System.out.println("Gambare, Goshujin-Sama ! ˚‧º·( 。ᗒ ‸ ◕✿) \n");
            }
            System.out.println("  [" + task.getStatusIcon() + "] "
                    + task.getDisplayDescription());
        } catch (NumberFormatException exception) {
            throw new ComputaException("Please provide a valid task number.");
        }
    }

    /**
     * Deletes a task selected by its one-based number.
     *
     * @param command the complete delete command.
     * @param tasks stored tasks.
     * @throws ComputaException if the command does not contain a valid task number.
     */
    private static void deleteTask(String command, ArrayList<Task> tasks) throws ComputaException {
        String[] parts = command.trim().split("\\s+");
        if (parts.length != 2) {
            throw new ComputaException("Please provide a valid task number.");
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new ComputaException("Please provide a valid task number.");
            }

            Task deletedTask = tasks.remove(taskNumber - 1);
            System.out.println("Goshujin-Sama, you don't want to do this with me anymore? "
                    + "(๑˃̣̣̥⌓˂̣̣̥)");
            System.out.println("  " + deletedTask);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("Not that I want to hang out with you anyway. (๑•́ ₃ •̀๑)");
        } catch (NumberFormatException exception) {
            throw new ComputaException("Please provide a valid task number.");
        }
    }
}
