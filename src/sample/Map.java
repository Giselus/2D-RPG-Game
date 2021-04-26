package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;
import sample.controllers.mainGameController;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {

    public class Camera{
        private double x,y;
        private double width;
        private double height;
        private double minX = 0;
        private double minY = 0;
        private double maxX = 32000;
        private double maxY = 32000;
        private float speed = 90;
        Camera(Canvas can){
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

    private ArrayList<Layer> layers;
    private Camera camera;
    private Layer selectedLayer;
    private File source;
    private String name;

    public Map(File source){
        this.source = source;
        layers = new ArrayList<>();
        camera = new Camera(mainGameController.instance.getCanvas());
        loadFromFile();
    }

    private void loadFromFile(){
        try{
            layers.clear();
            Scanner scanner = new Scanner(source);
            name = scanner.nextLine();
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            int layersNo = scanner.nextInt();
            for(int i = 0; i < layersNo;i++){
                Layer layer = new Layer(width,height);

                for(int y = 0; y < height;y++){
                    for(int x = 0; x < width;x++){
                        int tileId = scanner.nextInt();
                        if(tileId == 0)
                            continue;
                        layer.setTileAtPos(x,y,TileDatabase.getTile(tileId));
                    }
                }
                for(int y = 0; y < height;y++){
                    for(int x = 0; x < width;x++){
                        int blocked = scanner.nextInt();
                        if(blocked == 0)
                            continue;
                        layer.setCollisionAtPos( x,y,true);
                    }
                }
                int eventsNo = scanner.nextInt();
                for(int j = 1; j <= eventsNo;j++){
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    String code = scanner.next();
                    layer.addEvent(x,y,code);
                }

                layers.add(layer);
            }
            scanner.close();

        }catch(Exception e){
            System.out.println("Failed to load file: " + e);
        }
    }

    public Camera getCamera(){
        return camera;
    }

    public void Update(float deltaTime){
        HandleCameraMovement(deltaTime);
        Render();
    }

    private void HandleCameraMovement(float deltaTime){
        float deltaX = 0;
        float deltaY = 0;
        if(KeyPolling.isDown(KeyCode.A)){
            deltaX += -camera.speed * deltaTime;
        }
        if(KeyPolling.isDown(KeyCode.D)){
            deltaX += camera.speed * deltaTime;
        }
        if(KeyPolling.isDown(KeyCode.S)){
            deltaY += camera.speed * deltaTime;
        }
        if(KeyPolling.isDown(KeyCode.W)){
            deltaY += -camera.speed*deltaTime;
        }
        camera.addPosition(deltaX,deltaY);
    }

    private void Render(){
        mainGameController.instance.clearCanvas();
        for(int x = (int)camera.getX() / 32;x <= (int)(camera.getX()+camera.width)/32;x++) {
            for(int y = (int)camera.getY()/ 32;y <= (int)(camera.getY()+camera.height)/32;y++){
                for(Layer layer: layers) {
                    Tile tile = layer.getTileAtPos(x, y);
                    if (tile != null) {
                        int size = tile.getTileSet().getTileSize();
                        mainGameController.instance.setCanvas(tile, (int) ((x * 32) - camera.getX()), (int) ((y * 32) - camera.getY()));
                    }
                }
            }
        }
    }


}
