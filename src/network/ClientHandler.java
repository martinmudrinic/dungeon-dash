/*
@ASSESSME.USERID: mm2945
@ASSESSME.AUTHOR: Martin Mudrinic
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package network;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final GameServer server;

    private BufferedReader input;
    private PrintWriter output;

    private String username;
    private double x = 80;
    private double y = 80;

    public ClientHandler(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            String message;

            while ((message = input.readLine()) != null) {
                handleMessage(message);
            }

        } catch (IOException e) {
            System.out.println("Client disconnected.");
        } finally {
            close();
        }
    }

    private void handleMessage(String message) {

        if (message.startsWith(Protocol.JOIN + " ")) {
            username = message.substring(5).trim();

            server.broadcast(Protocol.PLAYER_JOINED + " " + username);
            server.broadcast(server.buildStateMessage());
        }

        else if (message.startsWith(Protocol.CHAT + " ")) {
            if (username == null) {
                return;
            }

            String chatMessage = message.substring(5);

            server.broadcast(
                    Protocol.CHAT_BROADCAST + " " + username + ": " + chatMessage
            );
        }

        else if (message.startsWith(Protocol.MOVE + " ")) {
            if (username == null) {
                return;
            }

            String[] parts = message.split(" ");

            if (parts.length == 3) {
                x = Double.parseDouble(parts[1]);
                y = Double.parseDouble(parts[2]);

                server.broadcast(server.buildStateMessage());
            }
        }
    }

    public void send(String message) {
        if (output != null) {
            output.println(message);
        }
    }

    public String getUsername() {
        return username;
    }

    public double getPlayerX() {
        return x;
    }

    public double getPlayerY() {
        return y;
    }

    private void close() {
        server.removeClient(this);

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Could not close socket.");
        }
    }
}