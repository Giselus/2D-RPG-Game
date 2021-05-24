package sample;


import sample.controllers.mainGameController;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    private ArrayList<Layer> layers;
    private File source;
    private String name;

    public Map(File source){
        this.source = source;
        layers = new ArrayList<>();
        loadFromFile();
    }

    private void loadFromFile(){
        try{
            layers.clear();
            Scanner scanner = new Scanner(source);
            name = scanner.nextLine();
            int width = scanner.nextInt();
            int height = scanner.nextInt();

            Camera.instance.maxX = (width) * 32;
            Camera.instance.maxY = (height) * 32;

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

    public void RenderLayer(int layerNo){
        if(layerNo > layers.size())
            return;
        Camera camera = Camera.instance;
        for(int x = (int)camera.getX() / 32;x <= (int)(camera.getX()+camera.width/camera.zoom)/32;x++) {
            for(int y = (int)camera.getY()/ 32;y <= (int)(camera.getY()+camera.height/camera.zoom)/32;y++){
                Layer layer = layers.get(layerNo-1);
                Tile tile = layer.getTileAtPos(x, y);
                if (tile != null) {
                    int size = tile.getTileSet().getTileSize();
                    mainGameController.instance.setCanvas(tile,
                            (int) ((x * 32) - camera.getX()), (int) ((y * 32) - camera.getY()));
                }
            }
        }
    }

    public int getLayersNo(){
        return layers.size();
    }

    public Layer getLayer(int id){
        if(id > layers.size())
            return null;
        return layers.get(id-1);
    }
}
