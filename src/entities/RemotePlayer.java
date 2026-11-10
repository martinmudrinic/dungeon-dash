package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Constants;

public class RemotePlayer extends GameObject {

    private final String username;
    private final Color color;

    public RemotePlayer(String username, double x, double y, Color color) {
        super(x, y, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
        this.username = username;
        this.color = color;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(getX(), getY(), width, height);

        gc.setFill(Color.WHITE);
        gc.fillText(username, getX() - 5, getY() - 5);
    }
}