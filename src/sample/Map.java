package sample;


import javafx.scene.image.Image;
import javafx.util.Pair;
import sample.controllers.mainGameController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Map {

    public enum EventType{
        STEP,
        PICK,
        DISTANCE_PICK
    }

    private ArrayList<Layer> layers;
    private File source;
    private String name;
    public HashMap<String, Pair<EventType,Action>> events;

    private static HashMap<String, Action> eventsMethods;

    public Map(File source){

        eventsMethods = new HashMap<>();
        eventsMethods.put("hatka_farmera", this::farmerHouse);
        eventsMethods.put("expowisko_jeden", this::farm);
        eventsMethods.put("maincity", this::city);
        eventsMethods.put("miastoone", this::shop);


        this.source = source;
        layers = new ArrayList<>();
        events = new HashMap<>();
        loadFromFile();
        eventsMethods.get(name).apply();
    }

    private void moveToScene(String name, int x, int y, int z){
        mapHandler.setCurrentMap(name);
        CharacterManager.instance.x = x;
        CharacterManager.instance.lastX = x;
        CharacterManager.instance.xPos = x * 32;
        CharacterManager.instance.y = y;
        CharacterManager.instance.lastY = y;
        CharacterManager.instance.yPos = y*32;
        CharacterManager.instance.z = z;
        CharacterManager.instance.setCameraPosition();
        if(CharacterManager.instance.animation != null)
            CharacterManager.instance.animation.Stop();
    }

    private void setPosition(GameObject object, int x, int y, int z){
        object.x = x;
        object.xPos = x*32;
        object.y = y;
        object.yPos = y*32;
        object.z = z;
    }

    private void farmerHouse(){
        events.put("goOut",  new Pair<>(EventType.PICK,()-> moveToScene(
                "expowisko_jeden",43,38,3)
        ));
    }

    private void farm(){
        events.put("toTown", new Pair<>(EventType.PICK,()->moveToScene(
                "maincity", 12,21,1)
        ));
        events.put("goInside", new Pair<>(EventType.DISTANCE_PICK, ()-> moveToScene(
                "hatka_farmera", 5,8,2)
        ));
        events.put("ladderUp", new Pair<>(EventType.STEP,()->CharacterManager.instance.z = 4));
        events.put("ladderDown", new Pair<>(EventType.STEP,()->CharacterManager.instance.z = 3));
    }

    private void city(){
        events.put("toFarm", new Pair<>(EventType.PICK, ()-> moveToScene(
                "expowisko_jeden",23,0,3)));
        events.put("goShop",new Pair<>(EventType.DISTANCE_PICK,()->moveToScene(
                "miastoone",9,13,2)));
        InteractiveObject guard1 = InteractiveObject.clone("CityGuard");
        InteractiveObject guard2 = InteractiveObject.clone("CityGuard");
        setPosition(guard1,14,17,1);
        setPosition(guard2,14,8,1);
    }

    private void shop(){
        events.put("goOut",new Pair<>(EventType.PICK,()->moveToScene(
                "maincity",4,8,1)));
        events.put("alchemistTalk",new Pair<>(EventType.DISTANCE_PICK,()->
            InteractiveObject.FetchByName("Alchemist").action.apply()));
        events.put("armourerTalk", new Pair<>(EventType.DISTANCE_PICK,()->
            InteractiveObject.FetchByName("Armourer").action.apply()));
        InteractiveObject alchemist = InteractiveObject.clone("Alchemist");
        setPosition(alchemist,14,2,2);
        InteractiveObject armourer = InteractiveObject.clone("Armourer");
        setPosition(armourer,1,2,2);
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
