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
            } else if (taskCount < MAX_TASKS) {
                tasks[taskCount] = new Task(command);
                taskCount++;
                System.out.println("added: " + command);
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
            System.out.println((i + 1) + ". " + tasks[i]);
        }
    }
}
