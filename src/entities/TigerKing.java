/*
@ASSESSME.USERID: rl3805
@ASSESSME.AUTHOR: Roko Lovreček
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package entities;

import game.DungeonMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Constants;

public class TigerKing extends TigerGuard {

    public TigerKing(double x, double y, DungeonMap dungeonMap) {

        super(x, y, dungeonMap);

        this.health = Constants.TIGER_KING_HEALTH;
        this.width = Constants.GUARD_SIZE * 1.5;
        this.height = Constants.GUARD_SIZE * 1.5;
    }

    @Override
    public void update(Player player) {

        double distance = position.distanceTo(player.getPosition());

        if (distance <= Constants.TIGER_KING_SIGHT_RADIUS) {
            chasing = true;
            chase(player);
        } else {
            chasing = false;
            patrol();
        }
    }

    @Override
    public void draw(GraphicsContext gc) {

        gc.setFill(chasing ? Color.DARKRED : Color.PURPLE);

        gc.fillOval(getX(), getY(), width, height);

        gc.setFill(Color.GOLD);
        gc.fillText("KING", getX() - 2, getY() - 5);
    }
}
