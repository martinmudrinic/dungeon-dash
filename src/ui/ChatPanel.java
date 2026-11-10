/*
@ASSESSME.USERID: rl3805
@ASSESSME.AUTHOR: Roko Lovreček
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import network.GameClient;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatPanel extends BorderPane {

    private final VBox messagesBox;
    private final ScrollPane scrollPane;
    private final TextField inputField;
    private final Button sendButton;

    private GameClient gameClient;

    public ChatPanel() {

        messagesBox = new VBox(5);
        messagesBox.setPadding(new Insets(10));

        scrollPane = new ScrollPane(messagesBox);
        scrollPane.setFitToWidth(true);

        inputField = new TextField();
        inputField.setPromptText("Type message...");

        sendButton = new Button("Send");

        HBox inputArea = new HBox(5, inputField, sendButton);
        inputArea.setPadding(new Insets(5));

        setCenter(scrollPane);
        setBottom(inputArea);
        setPrefWidth(300);

        sendButton.setOnAction(event -> sendMessage());
        inputField.setOnAction(event -> sendMessage());
    }

    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    private void sendMessage() {

        String message = inputField.getText().trim();

        if (message.isEmpty()) {
            return;
        }

        if (gameClient != null) {
            gameClient.sendChat(message);
        } else {
            addMessage("Local: " + message);
        }

        inputField.clear();
    }

    public void addMessage(String message) {

        String time = LocalTime.now().format(
                DateTimeFormatter.ofPattern("HH:mm")
        );

        Text text = new Text("[" + time + "] " + message);

        messagesBox.getChildren().add(text);

        scrollPane.setVvalue(1.0);
    }
}