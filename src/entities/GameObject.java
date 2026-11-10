/*
@ASSESSME.USERID: rl3805
@ASSESSME.AUTHOR: Roko Lovreček
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package entities;

import javafx.scene.canvas.GraphicsContext;
import util.Vector2;

public abstract class GameObject {
    protected Vector2 position;

    
    protected double width;
    protected double height;

    

    public GameObject(double x, double y, double width, double height){
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }

    public abstract void update();
    public abstract void draw(GraphicsContext gc);

    public Vector2 getPosition() {
        return position;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public void setPosition(double x, double y) {
        position.setX(x);
        position.setY(y);
    }

    public boolean intersects(GameObject other){
        return this.getX() < other.getX() + other.getWidth() 
            && this.getX() + this.getWidth() > other.getX()
            && this.getY() < other.getY() + other.getHeight()
            && this.getY() + this.getHeight() > other.getY();
    }
}
