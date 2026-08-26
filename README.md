# Computa

Computa is a command-line task manager written in Java. It stores tasks in
memory for the duration of a session and supports todos, deadlines, events,
completion status, deletion, and friendly handling of invalid input.

## Features

- Todos without date or time information.
- Deadlines with free-form or ISO due dates/times.
- Events with free-form or ISO start and end dates/times.
- Human-readable formatting and date queries for parsed ISO dates.
- List tasks with their type and completion status.
- Mark and unmark tasks.
- Delete tasks by their list number.
- Handle malformed or unknown commands without crashing.

Tasks are written to `data/computa.txt` whenever the task list changes and
loaded when Computa starts. Missing folders/files are created automatically.
Malformed records are skipped so corrupted data does not prevent startup.

## Commands

| Command | Example | Purpose |
| --- | --- | --- |
| `todo <description>` | `todo borrow book` | Add a todo. |
| `deadline <description> /by <date/time>` | `deadline submit report /by Friday` | Add a deadline. |
| `event <description> /from <start> /to <end>` | `event project meeting /from Mon 2pm /to 4pm` | Add an event. |
| `list` | `list` | Display all tasks. |
| `on <yyyy-mm-dd>` | `on 2019-10-15` | Display deadlines and events occurring on a date. |
| `mark <number>` | `mark 1` | Mark a task as completed. |
| `unmark <number>` | `unmark 1` | Mark a task as incomplete. |
| `delete <number>` | `delete 2` | Remove a task from the list. |
| `bye` | `bye` | Exit Computa. |

ISO dates (`yyyy-mm-dd`) and date-times (`yyyy-mm-dd HHmm` or ISO date-time
format) are stored as `java.time.LocalDateTime` values. The earlier
`d/M/yyyy HHmm` notation is also accepted. Parsed values are displayed as, for
example, `Oct 15 2019` or `Oct 15 2019 1800`. Free-form values such as `Sunday`
or `Mon 2pm` remain supported as text. The `on` command finds matching
deadlines and events; events match every date from their start through end date.

## Running in IntelliJ IDEA

Use JDK 25 and open this repository as an IntelliJ IDEA project. Then open
`src/main/java/Computa.java` and run `Computa.main()`.

## Running from a terminal

From the repository root, compile and run with Java 25:

```powershell
New-Item -ItemType Directory -Force _temp\classes | Out-Null
javac -encoding UTF-8 -d _temp\classes src\main\java\*.java
java "-Dstdout.encoding=UTF-8" "-Dstderr.encoding=UTF-8" -cp _temp\classes Computa
```

## UI tests

The documented end-to-end test cases are in
[`test/ui-test-plan.md`](test/ui-test-plan.md). Run them with the project-specific
`test-ui` skill:

```powershell
python .codex\skills\test-ui\scripts\run_ui_tests.py --plan test\ui-test-plan.md
```

The runner starts a fresh session for each case, prints the console transcript,
compares actual output with the expected output, and stops at the first failure.

## Project structure

- `src/main/java/Computa.java` — command loop and task management.
- `src/main/java/Task.java` — base task class.
- `src/main/java/Todo.java` — todo task type.
- `src/main/java/Deadline.java` — deadline task type.
- `src/main/java/Event.java` — event task type.
- `src/main/java/Ui.java` — console output and user-facing messages.
- `src/main/java/Command.java` — base type for executable commands.
- `src/main/java/ExitCommand.java` — command that ends a session.
- `src/main/java/Parser.java` — initial command-to-object conversion.
- `src/main/java/Storage.java` — writes and loads the task list to disk.
- `src/main/java/DateTimeParser.java` — parses and formats supported dates.
- `src/main/java/ComputaException.java` — user-input error type.
- `test/ui-test-plan.md` — UI test cases and expected transcripts.
