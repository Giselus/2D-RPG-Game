package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.*;


public class mainGameController {

    public static mainGameController instance;

    @FXML
    Canvas mapCanvas;

    @FXML
    void initialize(){
        if(instance == null) {
            TileDatabase.Init();
            mapHandler.Init();
            new RenderingManager();
            new Camera(mapCanvas);
            mapHandler.setCurrentMap("main");
        }
        instance = this;
        gc = mapCanvas.getGraphicsContext2D();
        RenderingManager.instance.setActive(true);
        CharacterManager.instance.setActive(true);
    }

    GraphicsContext gc;

    private int tileSize;
    private int xSize;
    private int ySize;

    public void clearCanvas(){
        gc.clearRect(0,0,mapCanvas.getWidth(),mapCanvas.getHeight());
    }

    public void setCanvas(Tile tile, int a, int b){
        Image img = tile.getTileSet().getImage();
        int size = tile.getTileSet().getTileSize();
        gc.drawImage(img, tile.getX() * size,tile.getY()*size,
                size,size,a,b,32,32);
    }

    public void drawImage(ImageFrame frame, float xPos, float yPos){
        gc.drawImage(frame.img,frame.left,frame.down,frame.xSize,frame.ySize,
                xPos - Camera.instance.getX(),
                yPos - Camera.instance.getY(),
                32,32);
    }

    public void drawCross(int x, int y){
        gc = mapCanvas.getGraphicsContext2D();
        Image img = new Image(getClass().getResource("/resources/textures/mark.png").toString());
        int size = 32;
        gc.drawImage(img,x,y,size,size);
    }

    public Canvas getCanvas(){
        return mapCanvas;
    }
}
