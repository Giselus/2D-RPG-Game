package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class InteractiveObject extends GameObject{

    public static HashSet<InteractiveObject> activeInteractiveObjects = new HashSet<>();
    public static HashMap<String, InteractiveObject> interactiveObjectsMap = new HashMap<>();
    public String name;
    public Action action;
    public boolean collision;
    public InteractiveObject(String name,Action action, boolean collision,ImageFrame ... img){
        super(0,0,0,img);
        this.name = name;
        this.action = action;
        this.collision = collision;
        setActive(false);
        interactiveObjectsMap.put(name,this);
    }
    private InteractiveObject(InteractiveObject toCopy){
        super(0,0,0,toCopy.images.toArray(new ImageFrame[toCopy.images.size()]));
        this.name = toCopy.name;
        this.action = toCopy.action;
        this.collision = toCopy.collision;
        setActive(true);
    }
    public static InteractiveObject clone(String name){
        if(interactiveObjectsMap.containsKey(name)){
            InteractiveObject toCopy = FetchByName(name);
            InteractiveObject tmp = new InteractiveObject(toCopy);
            return tmp;
        }
        return null;
    }
    public static InteractiveObject FetchByName(String name){
        if(interactiveObjectsMap.containsKey(name))
            return interactiveObjectsMap.get(name);
        return null;
    }
    public static void Initialize(){
        new InteractiveObject("Alchemist",
                ()->DialogueManager.instance.OpenDialogue("AlchemistDealer1b"), true,
                new ImageFrame(new Image(Main.class.getResource(
                        "/resources/textures/NPC/npc2.png").toString()),
                        320,128,64,64));
        new InteractiveObject("CityGuard",
                ()->DialogueManager.instance.OpenDialogue("CityGuard1b"), true,
                new ImageFrame(new Image(Main.class.getResource(
                        "/resources/textures/NPC/npc7.png").toString()),
                        0,640,64,64));
        new InteractiveObject("Armourer",
                ()->DialogueManager.instance.OpenDialogue("Armourer1b"), true,
                new ImageFrame(new Image(Main.class.getResource(
                        "/resources/textures/NPC/npc9.png").toString()),
                        0,384,64,64));
    }

    public void setActive(boolean active){
        super.setActive(active);
        if(active)
            activeInteractiveObjects.add(this);
        else
            activeInteractiveObjects.remove(this);
    }

    public static InteractiveObject getObject(int x, int y, int z){
        for(InteractiveObject object:activeInteractiveObjects){
            if(object.x == x && object.y == y && object.z == z && object.collision)
                return  object;
        }
        return null;
    }

}
