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

            if (command.equals("list")) {
                printTasks(tasks, taskCount);
            } else if (command.startsWith("mark ")) {
                updateTaskStatus(command, tasks, true);
            } else if (command.startsWith("unmark ")) {
                updateTaskStatus(command, tasks, false);
            } else if (taskCount < MAX_TASKS) {
                tasks[taskCount] = createTask(command);
                taskCount++;
                printAddedTask(tasks[taskCount - 1], taskCount);
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
    private static Task createTask(String command) {
        if (command.startsWith("todo ")) {
            return new Todo(command.substring("todo ".length()).trim());
        }

        if (command.startsWith("deadline ")) {
            String details = command.substring("deadline ".length());
            int byIndex = details.indexOf("/by");
            String description = details.substring(0, byIndex).trim();
            String by = details.substring(byIndex + "/by".length()).trim();
            return new Deadline(description, by);
        }

        if (command.startsWith("event ")) {
            String details = command.substring("event ".length());
            int fromIndex = details.indexOf("/from");
            int toIndex = details.indexOf("/to", fromIndex + 1);
            String description = details.substring(0, fromIndex).trim();
            String from = details.substring(fromIndex + "/from".length(), toIndex).trim();
            String to = details.substring(toIndex + "/to".length()).trim();
            return new Event(description, from, to);
        }

        return new Todo(command);
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
    private static void updateTaskStatus(String command, Task[] tasks, boolean isMark) {
        String[] parts = command.split(" ");
        int taskIndex = Integer.parseInt(parts[1]) - 1;
        if (isMark) {
            tasks[taskIndex].markAsDone();
        } else {
            tasks[taskIndex].markAsUndone();
        }
    }
}
