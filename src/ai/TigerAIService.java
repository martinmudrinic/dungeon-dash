/*
@ASSESSME.USERID: mm2945
@ASSESSME.AUTHOR: Martin Mudrinic
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package ai;

import entities.Player;
import entities.TigerGuard;
import javafx.application.Platform;

import java.util.List;

public class TigerAIService implements Runnable {

    private final List<TigerGuard> guards;
    private final Player player;
    private volatile boolean running;

    public TigerAIService(List<TigerGuard> guards, Player player) {
        this.guards = guards;
        this.player = player;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Platform.runLater(() -> {
                    if (!player.hasWon() && !player.isDead()) {
                        for (TigerGuard guard : guards) {
                            guard.update(player);
                        }
                    }
                });

                Thread.sleep(50);

            } catch (InterruptedException e) {
                running = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        running = false;
    }
}