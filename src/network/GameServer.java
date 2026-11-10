/*
@ASSESSME.USERID: mm2945
@ASSESSME.AUTHOR: Martin Mudrinic
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package network;

import util.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameServer {

    private final List<ClientHandler> clients;

    public GameServer() {
        clients = new CopyOnWriteArrayList<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {

            System.out.println("Dungeon Dash server started on port " + Constants.SERVER_PORT);

            while (true) {
                Socket socket = serverSocket.accept();

                ClientHandler handler = new ClientHandler(socket, this);
                clients.add(handler);

                Thread thread = new Thread(handler);
                thread.setDaemon(true);
                thread.start();
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    public void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.send(message);
        }
    }

    public String buildStateMessage() {
        StringBuilder builder = new StringBuilder(Protocol.STATE);

        for (ClientHandler client : clients) {
            if (client.getUsername() != null) {
                builder.append("|")
                        .append(client.getUsername()).append(",")
                        .append(client.getPlayerX()).append(",")
                        .append(client.getPlayerY());
            }
        }

        return builder.toString();
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);

        if (client.getUsername() != null) {
            broadcast(Protocol.PLAYER_LEFT + " " + client.getUsername());
            broadcast(buildStateMessage());
        }
    }

    public static void main(String[] args) {
        new GameServer().start();
    }
}