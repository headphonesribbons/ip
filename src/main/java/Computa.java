import java.util.Scanner;

/**
 * A minimal chatbot that echoes commands until the user says goodbye.
 */
public class Computa {

    /** Maximum number of tasks supported before the collection upgrade. */
    private static final int MAX_TASKS = 100;

    /** Separates the greeting and each command response in the console output. */
    private static final String SEPARATOR = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

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
                    printTasks(tasks, taskCount);
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    updateTaskStatus(command, tasks, taskCount, true);
                } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                    updateTaskStatus(command, tasks, taskCount, false);
                } else {
                    if (taskCount >= MAX_TASKS) {
                        throw new ComputaException("Your task list is full. Please complete some tasks first.");
                    }
                    tasks[taskCount] = createTask(command);
                    taskCount++;
                    printAddedTask(tasks[taskCount - 1], taskCount);
                }
            } catch (ComputaException exception) {
                System.out.println(exception.getMessage());
            }
            System.out.println(SEPARATOR);
        }
    }

    /**
     * Prints all tasks with their one-based list numbers.
     *
     * @param tasks stored tasks
     * @param taskCount number of tasks currently stored
     */
    private static void printTasks(Task[] tasks, int taskCount) {
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    /** Creates a task object from a todo, deadline, or event command. */
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

    /** Prints the acknowledgement shown after adding a task. */
    private static void printAddedTask(Task task, int taskCount) {
        System.out.println("More work? Don't overwork yourself, Goshujin-Sama ໒( ⇀ ‸ ↼ )७");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list. (⋟﹏⋞)");
        System.out.println("(.づ◡﹏◡)づ. When will we get some alone time together?");
    }

    /**
     * Marks or unmarks a task selected by its one-based number.
     *
     * @param command the complete mark or unmark command
     * @param tasks stored tasks
     * @param isMark true to mark the task, false to unmark it
     */
    private static void updateTaskStatus(String command, Task[] tasks, int taskCount, boolean isMark)
            throws ComputaException {
        String[] parts = command.trim().split("\\s+");
        if (parts.length != 2) {
            throw new ComputaException("Please provide a valid task number.");
        }

        int taskIndex;
        try {
            taskIndex = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException exception) {
            throw new ComputaException("Please provide a valid task number.");
        }
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new ComputaException("Please provide a valid task number.");
        }

        if (isMark) {
            tasks[taskIndex].markAsDone();
            System.out.println("Yatta! (ᗒᗨᗕ) I knew you could do it (✧ᴗ✧✿)");
        } else {
            tasks[taskIndex].markAsUndone();
            System.out.println("Gambare, Goshujin-Sama ! ˚‧º·( 。ᗒ ‸ ◕✿)");
        }
        System.out.println("  [" + tasks[taskIndex].getStatusIcon() + "] "
                + tasks[taskIndex].getDisplayDescription());
    }
}
