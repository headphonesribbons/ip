package computa.ui;

import computa.Computa;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Provides a JavaFX interface for interacting with Computa. */
public class ComputaGui extends Application {
    /** Chat feed containing the user and chatbot message bubbles. */
    private VBox conversation;
    /** Scroll container that keeps the newest chat bubble visible. */
    private ScrollPane conversationScrollPane;
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
        conversationScrollPane = new ScrollPane(conversation);
        conversationScrollPane.setFitToWidth(true);
        conversationScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        conversationScrollPane.setStyle("-fx-background: #f5f7fb; -fx-background-color: #f5f7fb;");
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
        root.setCenter(conversationScrollPane);
        root.setBottom(commandBar);
        BorderPane.setMargin(conversationScrollPane, new Insets(16, 0, 16, 0));

        computa = new Computa(new Ui(this::appendBotOutput));
        computa.startSession();

        stage.setTitle("Computa");
        stage.setScene(new Scene(root, 720, 600));
        stage.show();
        commandInput.requestFocus();
    }

    /** Creates the scrollable feed used to show the conversation bubbles. */
    private VBox createConversationView() {
        VBox chatFeed = new VBox(10);
        chatFeed.setPadding(new Insets(14));
        chatFeed.setFillWidth(true);
        chatFeed.setStyle("-fx-background-color: #f5f7fb;");
        return chatFeed;
    }

    /** Appends one chatbot output line to the conversation display. */
    private void appendBotOutput(String line) {
        if (line.startsWith("____")) {
            return;
        }
        appendBubble(line, false);
    }

    /** Sends the current input to Computa and disables controls after exit. */
    private void submitCommand() {
        String command = commandInput.getText().trim();
        if (command.isEmpty()) {
            return;
        }

        appendBubble(command, true);
        commandInput.clear();
        boolean shouldContinue = computa.processCommand(command);
        if (!shouldContinue) {
            commandInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }

    /** Adds a left-aligned chatbot bubble or right-aligned user bubble. */
    private void appendBubble(String text, boolean isUserMessage) {
        Label bubble = new Label(text);
        bubble.setWrapText(true);
        bubble.setMaxWidth(520);
        bubble.setStyle(isUserMessage
                ? "-fx-background-color: #d8ebff; -fx-background-radius: 16;"
                        + " -fx-padding: 10 14 10 14; -fx-font-size: 14px;"
                : "-fx-background-color: #ffffff; -fx-background-radius: 16;"
                        + " -fx-border-color: #e1e5ee; -fx-border-radius: 16;"
                        + " -fx-padding: 10 14 10 14; -fx-font-size: 14px;");

        HBox bubbleRow = new HBox(bubble);
        bubbleRow.setMaxWidth(Double.MAX_VALUE);
        bubbleRow.setAlignment(isUserMessage ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        conversation.getChildren().add(bubbleRow);
        Platform.runLater(() -> conversationScrollPane.setVvalue(1.0));
    }
}
