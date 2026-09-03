package computa.ui;

import computa.Computa;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Provides a JavaFX interface for interacting with Computa. */
public class ComputaGui extends Application {
    /** Conversation display shown above the command controls. */
    private TextArea conversation;
    /** Text field where the user enters a chatbot command. */
    private TextField commandInput;
    /** Button that submits the current command. */
    private Button sendButton;
    /** Chatbot instance shared by all commands in this window. */
    private Computa computa;

    /**
     * Builds and displays the Computa window.
     *
     * @param stage primary JavaFX window supplied by the platform.
     */
    @Override
    public void start(Stage stage) {
        conversation = createConversationView();
        commandInput = new TextField();
        commandInput.setPromptText("Enter a command, e.g. todo read a book");
        commandInput.setOnAction(event -> submitCommand());

        sendButton = new Button("Send");
        sendButton.setDefaultButton(true);
        sendButton.setOnAction(event -> submitCommand());

        HBox commandBar = new HBox(8, commandInput, sendButton);
        HBox.setHgrow(commandInput, Priority.ALWAYS);

        Label title = new Label("COMPUTA");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Label subtitle = new Label("Your personal task chatbot");
        subtitle.setStyle("-fx-text-fill: #666666;");
        VBox header = new VBox(2, title, subtitle);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));
        root.setTop(header);
        root.setCenter(conversation);
        root.setBottom(commandBar);
        BorderPane.setMargin(conversation, new Insets(16, 0, 16, 0));

        computa = new Computa(new Ui(this::appendBotOutput));
        computa.startSession();

        stage.setTitle("Computa");
        stage.setScene(new Scene(root, 720, 600));
        stage.show();
        commandInput.requestFocus();
    }

    /** Creates the read-only area used to show the conversation. */
    private TextArea createConversationView() {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");
        return textArea;
    }

    /** Appends one chatbot output line to the conversation display. */
    private void appendBotOutput(String line) {
        conversation.appendText(line + System.lineSeparator());
    }

    /** Sends the current input to Computa and disables controls after exit. */
    private void submitCommand() {
        String command = commandInput.getText().trim();
        if (command.isEmpty()) {
            return;
        }

        conversation.appendText("You: " + command + System.lineSeparator());
        commandInput.clear();
        boolean shouldContinue = computa.processCommand(command);
        if (!shouldContinue) {
            commandInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }
}
