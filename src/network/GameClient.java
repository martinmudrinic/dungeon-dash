/*
@ASSESSME.USERID: mm2945
@ASSESSME.AUTHOR: Martin Mudrinic
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package network;

import entities.RemotePlayer;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import ui.ChatPanel;
import util.Constants;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameClient {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private ChatPanel chatPanel;

    private String username;

    private final Map<String, RemotePlayer> remotePlayers =
            new ConcurrentHashMap<>();

    public void connect(String host, String username, ChatPanel chatPanel) {
        this.username = username;
        this.chatPanel = chatPanel;

        try {
            socket = new Socket(host, Constants.SERVER_PORT);

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            send(Protocol.JOIN + " " + username);

            Thread listenerThread = new Thread(this::listenForMessages);
            listenerThread.setDaemon(true);
            listenerThread.start();

        } catch (IOException e) {
            if (chatPanel != null) {
                chatPanel.addMessage("Could not connect to server.");
            }
        }
    }

    private void listenForMessages() {
        try {
            String message;

            while ((message = input.readLine()) != null) {
                String finalMessage = message;
                Platform.runLater(() -> handleServerMessage(finalMessage));
            }

        } catch (IOException e) {
            Platform.runLater(() -> {
                if (chatPanel != null) {
                    chatPanel.addMessage("Disconnected from server.");
                }
            });
        }
    }

    private void handleServerMessage(String message) {

        if (message.startsWith(Protocol.CHAT_BROADCAST)) {
            chatPanel.addMessage(message.substring(15));
        }

        else if (message.startsWith(Protocol.PLAYER_JOINED)) {
            chatPanel.addMessage(message.substring(15) + " joined the dungeon.");
        }

        else if (message.startsWith(Protocol.PLAYER_LEFT)) {
            String leftUser = message.substring(12).trim();
            remotePlayers.remove(leftUser);
            chatPanel.addMessage(leftUser + " left the dungeon.");
        }

        else if (message.startsWith(Protocol.STATE)) {
            parseState(message);
        }
    }

    private void parseState(String message) {
        String[] players = message.split("\\|");

        for (int i = 1; i < players.length; i++) {
            String[] data = players[i].split(",");

            if (data.length != 3) {
                continue;
            }

            String otherUsername = data[0];

            if (otherUsername.equals(username)) {
                continue;
            }

            double x = Double.parseDouble(data[1]);
            double y = Double.parseDouble(data[2]);

            RemotePlayer remotePlayer = remotePlayers.get(otherUsername);

            if (remotePlayer == null) {
                remotePlayers.put(
                        otherUsername,
                        new RemotePlayer(otherUsername, x, y, Color.MEDIUMPURPLE)
                );
            } else {
                remotePlayer.setPosition(x, y);
            }
        }
    }

    public Map<String, RemotePlayer> getRemotePlayers() {
        return remotePlayers;
    }

    public void sendChat(String message) {
        send(Protocol.CHAT + " " + message);
    }

    public void sendMove(double x, double y) {
        send(Protocol.MOVE + " " + x + " " + y);
    }

    private void send(String message) {
        if (output != null) {
            output.println(message);
        }
    }
}