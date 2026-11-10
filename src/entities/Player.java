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

public class Player extends GameObject{
    private final DungeonMap dungeonMap;

    private Color color;

    private int health;
    private int keysCollected;
    private int score;

    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;

    private boolean won;
    private boolean dead;


    

    public Player(double x, double y, Color color, DungeonMap dungeonMap) {
        super(x, y, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);

        this.color = color;
        this.dungeonMap = dungeonMap;

        this.health = Constants.PLAYER_MAX_HEALTH;
        this.keysCollected = 0;
        this.score = 0;
        this.won = false;
        
    }

    @Override
    public void update() {
        if(won){
            return;
        }
        double nextX = getX();
        double nextY = getY();

        if(moveUp){
            nextY -= Constants.PLAYER_SPEED;
        }

        if(moveDown){
            nextY += Constants.PLAYER_SPEED;
        }

        if(moveLeft){
            nextX -= Constants.PLAYER_SPEED;
        }

        if(moveRight){
            nextX += Constants.PLAYER_SPEED;
        }

        if(!dungeonMap.isWall(nextX, getY(), width, height)){
            position.setX(nextX);
        }

        if(!dungeonMap.isWall(getX(), nextY, width, height)){
            position.setY(nextY);
        }
        dungeonMap.revealTilesAroundPlayer(this);

        checkKeyCollection();
        checkExitUnlock();
        checkVictory();
    }

    private void checkKeyCollection(){
        double centerX = getX() + width / 2;
        double centerY = getY() + height /2;

        if(dungeonMap.collectKeyAt(centerX, centerY)){
            addKey();
        }
    }

    private void checkExitUnlock(){
        if(keysCollected >= dungeonMap.getTotalKeys()){
            dungeonMap.unlockExit();
        }
    }

    private void checkVictory(){
        double centerX = getX() + width / 2;
        double centerY = getY() + height / 2;

        if(dungeonMap.isExitUnlocked() && dungeonMap.isExitAt(centerX, centerY)){
            won = true;
            addScore(Constants.EXIT_POINTS);
        }
    }





    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);

        gc.fillOval(getX(), getY(), width, height);
    }

    public void damage(int amount){
        health -= amount;
        if(health < 0){
            health = 0;
        }
    }

    public void heal(int amount){
        health += amount;

        if(health > Constants.PLAYER_MAX_HEALTH){
            health = Constants.PLAYER_MAX_HEALTH;
        }
    }

    public void addKey(){
        keysCollected++;
        score += Constants.KEY_POINTS;
    }

    public void addScore(int points){
        score+=points;
    }

    public int getHealth() {
        return health;
    }

    public int getKeysCollected() {
        return keysCollected;
    }

    public int getScore() {
        return score;
    }

    public void setMoveUp(boolean moveUp){
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown){
        this.moveDown = moveDown;
    }

    public void setMoveLeft(boolean moveLeft){
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight){
        this.moveRight = moveRight;
    }

    public boolean hasWon() {
        return won;
    }

    public boolean isDead(){
        return health <= 0;
    }


    
}
