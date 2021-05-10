package sample;

import javafx.scene.canvas.Canvas;

public class Camera{
    public static Camera instance;

    private double x,y;
    public double width;
    public double height;
    private double minX = 0;
    private double minY = 0;
    private double maxX = 32000;
    private double maxY = 32000;
    public float speed = 90;
    Camera(Canvas can){
        instance = this;
        width = can.getWidth();
        height = can.getHeight();
        x = 0;
        y = 0;
    }

    public void addPosition(double x, double y){
        setPosition(this.x + x, this.y + y);
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
        if(this.x < minX)
            this.x = minX;
        if(this.y < minY)
            this.y = minY;
        if(this.x > maxX - width)
            this.x = maxX - width;
        if(this.y > maxY - height)
            this.y = maxY - height;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }
}
