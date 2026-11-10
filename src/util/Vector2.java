/*
@ASSESSME.USERID: mm2945
@ASSESSME.AUTHOR: Martin Mudrinic
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package util;


public class Vector2 {
    private double x;
    private double y;

    

    public Vector2(){
        this(0,0);
    }

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 other){
        this.x = other.x;
        this.y = other.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void add(double dx, double dy){
        this.x += dx;
        this.y += dy;
    }

    public double distanceTo(Vector2 other){
        double dx = other.x - this.x;
        double dy = other.y - this.y;

        return Math.sqrt(dx*dx+dy*dy);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
