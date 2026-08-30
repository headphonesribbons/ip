package computa;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import computa.command.Command;
import computa.command.Parser;
import computa.exception.ComputaException;
import computa.storage.Storage;
import computa.task.Deadline;
import computa.task.Event;
import computa.task.Task;
import computa.task.Todo;
import computa.ui.Ui;
import computa.util.DateTimeParser;

/**
 * A minimal chatbot that stores tasks and displays them when requested.
 */
public class Computa {
    /** Relative path of the file used to store tasks. */
    private static final String FILE_PATH = "." + File.separator + "data" + File.separator + "computa.txt";

    /** Reads and writes the task data file. */
    private final Storage storage;
    /** In-memory tasks used by the current Computa session. */
    private final ArrayList<Task> tasks;
    /** Handles user-facing console output. */
    private final Ui ui;

    /** Creates an empty task list and prepares its data file. */
    public Computa() {
        storage = new Storage(FILE_PATH);
        storage.initialiseDataFile();
        tasks = storage.loadTasks();
        ui = new Ui();
    }

    /**
     * Starts the interactive command loop and processes input until the user exits.
     * Invalid commands are converted into friendly error messages while the loop
     * continues so that one bad input does not terminate the session.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        ui.showGreeting();

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            ui.showSeparator();

            Command parsedCommand = Parser.parse(command);
            if (parsedCommand != null) {
                try {
                    parsedCommand.execute(tasks, ui, storage);
                } catch (ComputaException exception) {
                    ui.showError(exception.getMessage());
                }
                if (parsedCommand.isExit()) {
                    ui.showSeparator();
                    break;
                }
                ui.showSeparator();
                continue;
            }

            try {
                if (command.equals("list")) {
                    ui.showTasks(tasks);
                } else if (command.equals("on") || command.startsWith("on ")) {
                    showTasksOnDate(command);
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    Task updatedTask = updateTaskStatus(command, tasks, true);
                    storage.saveTasks(tasks);
                    ui.showStatusUpdate(updatedTask, true);
                } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                    Task updatedTask = updateTaskStatus(command, tasks, false);
                    storage.saveTasks(tasks);
                    ui.showStatusUpdate(updatedTask, false);
                } else if (command.equals("delete") || command.startsWith("delete ")) {
                    Task deletedTask = deleteTask(command, tasks);
                    storage.saveTasks(tasks);
                    ui.showDeletedTask(deletedTask, tasks.size());
                } else {
                    Task newTask = createTask(command);
                    tasks.add(newTask);
                    storage.saveTasks(tasks);
                    ui.showAddedTask(newTask, tasks.size());
                }
            } catch (ComputaException exception) {
                ui.showError(exception.getMessage());
            }

            ui.showSeparator();
        }
    }

    /**
     * Prints Computa's greeting, then processes commands until {@code bye} is entered.
     *
     * @param args command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        new Computa().run();
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
                throw new ComputaException("Hmph! A deadline needs a description and a /by date or time. \n"
                        + " Do I HAVE to help you with everything?");
            }
            String description = details.substring(0, byIndex).trim();
            String by = details.substring(byIndex + "/by".length()).trim();
            if (description.isEmpty() || by.isEmpty()) {
                throw new ComputaException("Hmph! A deadline needs a description and a /by date or time. \n"
                        + " Do I HAVE to help you with everything?");
            }
            if (DateTimeParser.looksLikeDate(by) && DateTimeParser.parse(by) == null) {
                throw new ComputaException("Hmph! I can't understand that deadline date. Use yyyy-mm-dd.");
            }
            return new Deadline(description, by);
        }

        if (command.equals("event") || command.startsWith("event ")) {
            String details = command.substring("event".length()).trim();
            int fromIndex = details.indexOf("/from");
            int toIndex = details.indexOf("/to", fromIndex + 1);
            if (fromIndex < 0 || toIndex < 0) {
                throw new ComputaException(
                        "Hmph! An event needs a description, /from date or time, and "
                                + "/to date or time. \n"
                                + " Do I HAVE to help you with everything?");
            }
            String description = details.substring(0, fromIndex).trim();
            String from = details.substring(fromIndex + "/from".length(), toIndex).trim();
            String to = details.substring(toIndex + "/to".length()).trim();
            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new ComputaException(
                        "Hmph! An event needs a description, /from date or time, and "
                                + "/to date or time. \n"
                                + " Do I HAVE to help you with everything?");
            }
            if ((DateTimeParser.looksLikeDate(from) && DateTimeParser.parse(from) == null)
                    || (DateTimeParser.looksLikeDate(to) && DateTimeParser.parse(to) == null)) {
                throw new ComputaException("Hmph! I can't understand that event date. Use yyyy-mm-dd.");
            }
            LocalDateTime parsedFrom = DateTimeParser.parse(from);
            LocalDateTime parsedTo = DateTimeParser.parse(to);
            if (parsedFrom != null && parsedTo != null && parsedFrom.isAfter(parsedTo)) {
                throw new ComputaException("Hmph! An event cannot end before it starts.");
            }
            return new Event(description, from, to);
        }

        throw new ComputaException("Hmph! Making small talk won't get you anywhere.  ʕ ꈍᴥꈍʔ");
    }

    /** Parses a date query and delegates its display to the UI. */
    private void showTasksOnDate(String command) throws ComputaException {
        String dateText = command.substring("on".length()).trim();
        LocalDate date = DateTimeParser.parseQueryDate(dateText);
        if (date == null) {
            throw new ComputaException("Hmph! Enter a date in yyyy-mm-dd format.");
        }

        ui.showTasksOnDate(tasks, date);
    }

    /**
     * Marks or unmarks a task selected by its one-based number.
     *
     * @param command the complete mark or unmark command.
     * @param tasks stored tasks.
     * @param isMark true to mark the task, false to unmark it.
     */
    private static Task updateTaskStatus(String command, ArrayList<Task> tasks, boolean isMark)
            throws ComputaException {
        String[] parts = command.trim().split("\\s+");
        if (parts.length != 2) {
            throw new ComputaException("TOMARE!!!! Don't think you can mark tasks without doing them.");
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new ComputaException("TOMARE!!!! Don't think you can mark tasks without doing them.");
            }

            int taskIndex = taskNumber - 1;
            Task task = tasks.get(taskIndex);
            if (isMark) {
                task.markAsDone();
            } else {
                task.markAsUndone();
            }
            return task;
        } catch (NumberFormatException exception) {
            throw new ComputaException("TOMARE!!!! Don't think you can mark tasks without doing them.");
        }
    }

    /**
     * Deletes a task selected by its one-based number.
     *
     * @param command the complete delete command.
     * @param tasks stored tasks.
     * @throws ComputaException if the command does not contain a valid task number.
     */
    private static Task deleteTask(String command, ArrayList<Task> tasks) throws ComputaException {
        String[] parts = command.trim().split("\\s+");
        if (parts.length != 2) {
            throw new ComputaException("TOMARE!!!! Don't think you can mark tasks without doing them.");
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new ComputaException("TOMARE!!!! Don't think you can mark tasks without doing them.");
            }

            return tasks.remove(taskNumber - 1);
        } catch (NumberFormatException exception) {
            throw new ComputaException("TOMARE!!!! Don't think you can mark tasks without doing them.");
        }
    }
}
