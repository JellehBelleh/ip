package ubis;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main JavaFX application entry point for the Ubis chatbot GUI.
 */
public class Main extends Application {
    private final Ubis ubis = new Ubis();
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;

    @Override
    public void start(Stage stage) {
        AnchorPane mainLayout = initializeComponents();
        configureStage(stage, new Scene(mainLayout));
        configureLayout(mainLayout);
        configureEventHandlers(stage);
        showWelcomeMessage();
        stage.show();
    }

    /**
     * Creates the controls used by the main window and places them in the root layout.
     *
     * @return Root layout containing the main window controls.
     */
    private AnchorPane initializeComponents() {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        dialogContainer.setSpacing(10);
        dialogContainer.setPadding(new Insets(10));
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        userInput.setPromptText("Type a command...");
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);
        return mainLayout;
    }

    /**
     * Configures the primary stage properties.
     *
     * @param stage Primary application stage.
     * @param scene Scene displayed by the stage.
     */
    private void configureStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setTitle("Ubis Chatbot");
        stage.setResizable(true);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);
    }

    /**
     * Configures the preferred sizes, scroll behavior, and anchors of the main layout.
     *
     * @param mainLayout Root layout containing the main window controls.
     */
    private void configureLayout(AnchorPane mainLayout) {
        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);
        sendButton.setPrefWidth(55.0);

        // Configure anchor pane layout constraints
        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setLeftAnchor(scrollPane, 1.0);
        AnchorPane.setRightAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(scrollPane, 45.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
        AnchorPane.setRightAnchor(userInput, 65.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
    }

    /**
     * Registers listeners and handlers for user interaction with the main window.
     *
     * @param stage Primary application stage used when handling user input.
     */
    private void configureEventHandlers(Stage stage) {
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        sendButton.setOnMouseClicked((event) -> handleUserInput(stage));
        userInput.setOnAction((event) -> handleUserInput(stage));
    }

    /**
     * Adds the initial welcome greeting to the dialog area.
     */
    private void showWelcomeMessage() {
        dialogContainer.getChildren().add(DialogBox.getUbisDialog(Ui.getWelcomeMessage()));
    }

    /**
     * Handles the user interaction when sending a message through the text field or send button.
     *
     * @param stage Primary application stage.
     */
    private void handleUserInput(Stage stage) {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        String response = ubis.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getUbisDialog(response)
        );
        userInput.clear();

        if ("bye".equalsIgnoreCase(input.trim())) {
            Platform.exit();
        }
    }
}
