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
    public InteractiveObject(InteractiveObject toCopy){
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
        new InteractiveObject("Father",
                ()->DialogueManager.instance.OpenDialogue("FatherStartPhase"), true,
                new ImageFrame(new Image(Main.class.getResource(
                        "/resources/textures/NPC/npc8.png").toString()),
                        0,640,64,64));
        new InteractiveObject("Boss",
                ()->DialogueManager.instance.OpenDialogue("Boss1b"),true,
                new ImageFrame(new Image(Main.class.getResource(
                        "/resources/textures/Enemies/BOSS1.png").toString()),
                        0,11*64,64,64));
        new InteractiveObject("Skeleton archer",()->{},true,new ImageFrame(
                new Image(Main.class.getResource("/resources/textures/Enemies/NORMAL1.png").toString()),
                64,640,64,64));
        new InteractiveObject("Werewolf",()->{},true,new ImageFrame(
                new Image(Main.class.getResource("/resources/textures/Enemies/NORMAL2.png").toString()),
                64,640,64,64));
        new InteractiveObject("Orc miner",()->{},true,new ImageFrame(
                new Image(Main.class.getResource("/resources/textures/Enemies/NORMAL3.png").toString()),
                64,640,64,64));
        new InteractiveObject("Zombie",()->{},true,new ImageFrame(
                new Image(Main.class.getResource("/resources/textures/Enemies/NORMAL4.png").toString()),
                64,640,64,64));
        new InteractiveObject("Reptile",()->{},true,new ImageFrame(
                new Image(Main.class.getResource("/resources/textures/Enemies/NORMAL5.png").toString()),
                64,640,64,64));
    }

    public void setActive(boolean active){
        super.setActive(active);
        if(active)
            activeInteractiveObjects.add(this);

    }

    public static InteractiveObject getObject(int x, int y, int z){
        for(InteractiveObject object:activeInteractiveObjects){
            if(object.x == x && object.y == y && object.z == z && object.collision)
                return  object;
        }
        return null;
    }
}
