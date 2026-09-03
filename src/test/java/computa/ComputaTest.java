package computa;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import computa.storage.Storage;
import computa.ui.Ui;

/** Tests the command-processing adapter shared by the console and GUI. */
class ComputaTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void processCommand_todoThenList_sendsResponsesToConfiguredUi() {
        ArrayList<String> output = new ArrayList<>();
        Storage storage = new Storage(temporaryDirectory.resolve("tasks.txt").toString());
        Computa computa = new Computa(storage, new Ui(output::add));

        assertTrue(computa.processCommand("todo read book"));
        assertTrue(computa.processCommand("list"));

        assertTrue(output.stream().anyMatch(line -> line.contains("read book")));
        assertTrue(output.stream().anyMatch(line -> line.contains("1.[T][ ] read book")));
    }

    @Test
    void processCommand_bye_returnsFalseAndSendsFarewell() {
        ArrayList<String> output = new ArrayList<>();
        Storage storage = new Storage(temporaryDirectory.resolve("tasks.txt").toString());
        Computa computa = new Computa(storage, new Ui(output::add));

        assertFalse(computa.processCommand("bye"));

        assertTrue(output.stream().anyMatch(line -> line.contains("Noooo don't go")));
    }
}
