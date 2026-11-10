/*
@ASSESSME.USERID: rl3805
@ASSESSME.AUTHOR: Roko Lovreček
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package ui;

import entities.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import util.Constants;

public class HUDRenderer {

    public void draw(GraphicsContext gc, Player player) {

        gc.setFill(Color.rgb(0, 0, 0, 0.65));

        gc.fillRect(10, 10, 220, 90);

        gc.setFill(Color.WHITE);

        gc.setFont(Font.font("Arial", 18));

        gc.fillText("Health: " + player.getHealth(), 25, 35);

        gc.fillText("Keys: " + player.getKeysCollected(), 25, 60);

        gc.fillText("Score: " + player.getScore(), 25, 85);
    }

    public void drawVictory(GraphicsContext gc, Player player) {

        gc.setFill(Color.rgb(0, 0, 0, 0.75));

        gc.fillRect(
                0,
                0,
                Constants.CANVAS_WIDTH,
                Constants.CANVAS_HEIGHT
        );

        gc.setFill(Color.LIMEGREEN);

        gc.setFont(Font.font("Arial", 44));

        gc.fillText("VICTORY!", 290, 260);

        gc.setFill(Color.WHITE);

        gc.setFont(Font.font("Arial", 24));

        gc.fillText(
                "Final Score: " + player.getScore(),
                310,
                310
        );
    }

    public void drawGameOver(GraphicsContext gc, Player player) {

        gc.setFill(Color.rgb(0, 0, 0, 0.75));

        gc.fillRect(
                0,
                0,
                Constants.CANVAS_WIDTH,
                Constants.CANVAS_HEIGHT
        );

        gc.setFill(Color.RED);

        gc.setFont(Font.font("Arial", 44));

        gc.fillText("GAME OVER", 260, 260);

        gc.setFill(Color.WHITE);

        gc.setFont(Font.font("Arial", 24));

        gc.fillText(
                "Final Score: " + player.getScore(),
                310,
                310
        );
    }
}