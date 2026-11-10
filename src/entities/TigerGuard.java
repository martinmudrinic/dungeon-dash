/*
@ASSESSME.USERID: mm2945
@ASSESSME.AUTHOR: Martin Mudrinic
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package entities;

import game.DungeonMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Constants;

public class TigerGuard extends GameObject {

    protected final DungeonMap dungeonMap;

    protected int health;
    protected boolean chasing;

    private int directionX;
    private int directionY;

    public TigerGuard(double x,
                      double y,
                      DungeonMap dungeonMap) {

        super(x, y,
                Constants.GUARD_SIZE,
                Constants.GUARD_SIZE);

        this.dungeonMap = dungeonMap;

        this.health = 100;
        this.chasing = false;

        this.directionX = 1;
        this.directionY = 0;
    }

    @Override
    public void update() {
        patrol();
    }

    public void update(Player player) {

    if (player.isDead() || player.hasWon()) {
        chasing = false;
        return;
    }

    double distance = position.distanceTo(player.getPosition());

        if(distance <= Constants.GUARD_SIGHT_RADIUS){

            chasing = true;

            chase(player);

        } else {

            chasing = false;

            patrol();
        }
    }

    protected void patrol(){

        double nextX =
                getX() + directionX * Constants.GUARD_SPEED;

        double nextY =
                getY() + directionY * Constants.GUARD_SPEED;

        if(dungeonMap.isWall(nextX, nextY, width, height)){

            turnRight();

        } else {

            setPosition(nextX, nextY);
        }
    }

    protected void chase(Player player){

        double dx = player.getX() - getX();
        double dy = player.getY() - getY();

        double length = Math.sqrt(dx * dx + dy * dy);

        if(length == 0){
            return;
        }

        dx /= length;
        dy /= length;

        double nextX =
                getX() + dx * Constants.GUARD_SPEED;

        double nextY =
                getY() + dy * Constants.GUARD_SPEED;

        if(!dungeonMap.isWall(nextX, nextY, width, height)){

            setPosition(nextX, nextY);
        }
    }

    private void turnRight(){

        if(directionX == 1 && directionY == 0){

            directionX = 0;
            directionY = 1;

        } else if(directionX == 0 && directionY == 1){

            directionX = -1;
            directionY = 0;

        } else if(directionX == -1 && directionY == 0){

            directionX = 0;
            directionY = -1;

        } else {

            directionX = 1;
            directionY = 0;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {

        if(chasing){

            gc.setFill(Color.RED);

        } else {

            gc.setFill(Color.ORANGE);
        }

        gc.fillOval(getX(), getY(), width, height);
    }

    public boolean isChasing() {
        return chasing;
    }

    public int getHealth() {
        return health;
    }

    public void damage(int amount){

        health -= amount;

        if(health < 0){
            health = 0;
        }
    }
}