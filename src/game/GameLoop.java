/*
@ASSESSME.USERID: mm2945
@ASSESSME.AUTHOR: Martin Mudrinic
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package game;

import entities.Player;
import entities.RemotePlayer;
import entities.TigerGuard;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import network.GameClient;
import ui.HUDRenderer;

import java.util.List;

public class GameLoop extends AnimationTimer {

    private final Canvas canvas;
    private final GraphicsContext gc;

    private final DungeonMap dungeonMap;
    private final Player player;
    private final List<TigerGuard> guards;
    private final HUDRenderer hudRenderer;
    private final GameClient gameClient;

    public GameLoop(Canvas canvas,
                    DungeonMap dungeonMap,
                    Player player,
                    List<TigerGuard> guards,
                    HUDRenderer hudRenderer,
                    GameClient gameClient) {

        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        this.dungeonMap = dungeonMap;
        this.player = player;
        this.guards = guards;
        this.hudRenderer = hudRenderer;
        this.gameClient = gameClient;
    }

    @Override
    public void handle(long now) {
        update();
        draw();
    }

    private void update() {
        if (!player.hasWon() && !player.isDead()) {
            player.update();

            gameClient.sendMove(
                    player.getX(),
                    player.getY()
            );
        }

        for (TigerGuard guard : guards) {
            if (!player.hasWon()
                    && !player.isDead()
                    && player.intersects(guard)) {

                player.damage(1);
            }
        }
    }

    private void draw() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        dungeonMap.draw(gc);

        for (RemotePlayer remotePlayer : gameClient.getRemotePlayers().values()) {
            remotePlayer.draw(gc);
        }

        for (TigerGuard guard : guards) {
            guard.draw(gc);
        }

        player.draw(gc);

        hudRenderer.draw(gc, player);

        if (player.hasWon()) {
            hudRenderer.drawVictory(gc, player);
        }

        if (player.isDead()) {
            hudRenderer.drawGameOver(gc, player);
        }
    }
}