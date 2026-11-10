/*
@ASSESSME.USERID: rl3805
@ASSESSME.AUTHOR: Roko Lovreček
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package main;

import ai.TigerAIService;
import entities.Player;
import entities.TigerGuard;
import entities.TigerKing;
import game.DungeonMap;
import game.GameLoop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import network.GameClient;
import ui.ChatPanel;
import ui.HUDRenderer;
import util.Constants;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends Application {

    private TigerAIService tigerAIService;

    @Override
    public void start(Stage primaryStage) {

        Canvas canvas = new Canvas(
                Constants.CANVAS_WIDTH,
                Constants.CANVAS_HEIGHT
        );

        DungeonMap dungeonMap = new DungeonMap();

        Player player = new Player(
                80,
                80,
                Color.CORNFLOWERBLUE,
                dungeonMap
        );

        List<TigerGuard> guards = new CopyOnWriteArrayList<>();
        guards.add(new TigerGuard(300, 120, dungeonMap));
        guards.add(new TigerGuard(500, 420, dungeonMap));
        guards.add(new TigerKing(680, 500, dungeonMap));

        HUDRenderer hudRenderer = new HUDRenderer();

        ChatPanel chatPanel = new ChatPanel();

        GameClient gameClient = new GameClient();

        String username = "Player" + System.currentTimeMillis() % 1000;

        gameClient.connect("localhost", username, chatPanel);

        chatPanel.setGameClient(gameClient);

        GameLoop gameLoop = new GameLoop(
            canvas,
            dungeonMap,
            player,
            guards,
            hudRenderer,
            gameClient
        );

        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setRight(chatPanel);

        Scene scene = new Scene(
                root,
                Constants.WINDOW_WIDTH,
                Constants.WINDOW_HEIGHT
        );

        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();

            switch (key) {
                case W:
                    player.setMoveUp(true);
                    break;
                case S:
                    player.setMoveDown(true);
                    break;
                case A:
                    player.setMoveLeft(true);
                    break;
                case D:
                    player.setMoveRight(true);
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode key = event.getCode();

            switch (key) {
                case W:
                    player.setMoveUp(false);
                    break;
                case S:
                    player.setMoveDown(false);
                    break;
                case A:
                    player.setMoveLeft(false);
                    break;
                case D:
                    player.setMoveRight(false);
                    break;
            }
        });

        scene.setOnMouseClicked(event -> root.requestFocus());

        primaryStage.setTitle("Dungeon Dash & the Tiger's Lair");
        primaryStage.setScene(scene);
        primaryStage.show();

        root.requestFocus();

        gameLoop.start();

        tigerAIService = new TigerAIService(guards, player);

        Thread aiThread = new Thread(tigerAIService);
        aiThread.setDaemon(true);
        aiThread.start();
    }

    @Override
    public void stop() {
        if (tigerAIService != null) {
            tigerAIService.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}