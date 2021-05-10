package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sample.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class mainGameController {

    public static mainGameController instance;

    @FXML
    Canvas mapCanvas;

    @FXML
    void initialize(){
        instance = this;
        TileDatabase.Init();
        mapHandler.Init();
        mapHandler.setCurrentMap("test");
        //TODO: create character
        /*ImageFrame base = new ImageFrame(new Image(getClass().getResource("/resources/textures/Items/BOOTS0.png").toString()));
        GameObject test = new GameObject(base,32,32);
        ImageFrame base2 = new ImageFrame(new Image(getClass().getResource("/resources/textures/Items/BOOTS1.png").toString()));
        ImageFrame base3 = new ImageFrame(new Image(getClass().getResource("/resources/textures/Items/BOOTS2.png").toString()));
        test.animation = new Animation(1,128,128,base,base2,base3);
        test.animation.Play(test);*/
    }

    GraphicsContext gc;

    private int tileSize;
    private int xSize;
    private int ySize;

    public void clearCanvas(){
        gc = mapCanvas.getGraphicsContext2D();
        gc.clearRect(0,0,mapCanvas.getWidth(),mapCanvas.getHeight());
    }

    public void setCanvas(Tile tile, int a, int b){
        gc = mapCanvas.getGraphicsContext2D();
        Image img = tile.getTileSet().getImage();
        int size = tile.getTileSet().getTileSize();
        gc.drawImage(img, tile.getX() * size,tile.getY()*size,
                size,size,a,b,32,32);
    }

    public void drawImage(ImageFrame frame, float xPos, float yPos){
        gc.drawImage(frame.img,frame.left,frame.down,frame.xSize,frame.ySize,
                xPos - Camera.instance.getX(),
                yPos - Camera.instance.getY(),
                frame.xSize,frame.ySize);
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
