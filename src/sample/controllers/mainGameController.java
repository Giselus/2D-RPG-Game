package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.*;

import java.util.ArrayList;


public class mainGameController {

    public static mainGameController instance;

    @FXML
    AnchorPane mainPane;

    @FXML
    Canvas mapCanvas;

    @FXML
    void initialize(){
        if(instance == null) {
            TileDatabase.Init();
            mapHandler.Init();
            new RenderingManager();
            new Camera(mapCanvas);
            new EnemyManager();
            GameVariable.Initialize();
            new DialogueManager();
            mapHandler.setCurrentMap("hatka_farmera");
        }
        instance = this;
        gc = mapCanvas.getGraphicsContext2D();
        RenderingManager.instance.setActive(true);
        CharacterManager.instance.setActive(true);
        EnemyManager.instance.setActive(true);
        DialogueManager.instance.setActive(true);

    }

    GraphicsContext gc;

    private int tileSize;
    private int xSize;
    private int ySize;

    public void clearCanvas(){
        gc.clearRect(0,0,mapCanvas.getWidth(),mapCanvas.getHeight());
    }

    public void setCanvas(Tile tile, float a, float b){
        float zoom = Camera.instance.zoom;
        Image img = tile.getTileSet().getImage();
        int size = tile.getTileSet().getTileSize();
        gc.drawImage(img, tile.getX() * size,tile.getY()*size,
                size,size,a*zoom,b*zoom,32* zoom,32 * zoom);
    }

    private ScrollPane dialogueBox;
    private VBox vBox;

    public void openDialogueBox(){
        dialogueBox = new ScrollPane();
        dialogueBox.setMaxSize(1280,180);
        dialogueBox.setMinSize(1280,180);
        dialogueBox.setLayoutY(540);
        vBox = new VBox();
        dialogueBox.setContent(vBox);
        mainPane.getChildren().add(dialogueBox);
    }

    public void setDialogues(String mainDialogue, ArrayList<String> options){
        vBox.getChildren().clear();
        Text npcText = new Text();
        npcText.setWrappingWidth(800);
        npcText.setText(mainDialogue);
        vBox.getChildren().add(npcText);
        int id = 0;
        for(String option: options){
            id++;
            Text dialogueOption = new Text();
            dialogueOption.setText(String.format("(%d)%s",id,option));
            dialogueOption.setWrappingWidth(800);
            vBox.getChildren().add(dialogueOption);
        }
    }

    public void closeDialogueBox(){
        mainPane.getChildren().remove(dialogueBox);
    }

    public void drawImage(ImageFrame frame, float xPos, float yPos){
        float zoom = Camera.instance.zoom;
        gc.drawImage(frame.img,frame.left,frame.down,frame.xSize,frame.ySize,
                (xPos - Camera.instance.getX()) * zoom,
                (yPos - Camera.instance.getY()) * zoom,
                32 * zoom,32 * zoom);
    }

    public void drawCross(int x, int y){
        //TODO: Take camera zoom
        gc = mapCanvas.getGraphicsContext2D();
        Image img = new Image(getClass().getResource("/resources/textures/mark.png").toString());
        int size = 32;
        gc.drawImage(img,x,y,size,size);
    }

    public Canvas getCanvas(){
        return mapCanvas;
    }
}
