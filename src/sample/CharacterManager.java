package sample;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class CharacterManager extends GameObject{

    public static CharacterManager instance;
    int x, y;
    String name;
    int attack;
    int defense;
    int luck;
    int agility;
    int hp;
    int mana;
    int stamina;
    public Image skin;
    public Image legs;
    public Image body;
    public Image hair;
    public Image helmet;
    public Image armor;
    public Image boots;
    public InventoryPlayer inventory;
    public ArrayList<Skills> skills;
    public boolean hasHelmet;
    public boolean hasArmor;
    public boolean hasBoots;

    //test_line
    public ContainerForNpc interactiveChest;
    public CharacterManager(){
        instance = this;
    }

    public CharacterManager(String name,int attack,int defense,int luck,
            int agility,int mana,int stamina,int hp,Image skin,Image legs,Image body,Image hair,
                            int xPos, int yPos, int zPos){
        super(xPos * 32,yPos * 32,zPos,new ImageFrame(skin,0,640,64,64),
                new ImageFrame(legs,0,640,64,64),
                new ImageFrame(body,0,640,64,64),
                new ImageFrame(hair,0,640,64,64));
        if(skin == null){
            System.out.println("PROBLEM");
        }
        x = xPos;
        y = yPos;
        instance = this;
        this.name=name;
        this.attack=attack;
        this.defense=defense;
        this.luck=luck;
        this.agility=agility;
        this.hp=hp;
        this.mana=mana;
        this.stamina=stamina;
        this.skin=skin;
        this.legs=legs;
        this.body=body;
        this.hair=hair;
        inventory = new InventoryPlayer(4, 4);
        skills = new ArrayList<>(4);
        hasArmor = false;
        hasHelmet = false;
        hasBoots = false;

        //testing lines, this functions are essential for testing inventory and battle
        interactiveChest = new ContainerForNpc(0, 0, 4, 4);
        interactiveChest.inventory.addItem(new Items(1, 2));
        interactiveChest.inventory.addItem(new Items(3, 0));
        inventory.addItem(new Items(1,1));
        inventory.addItem(new Items(2,0));
        inventory.addItem(new Items(3,2));
        inventory.addItem(new Items(4,0));
        inventory.addItem(new Items(1,0));
        skills.add(new Skills(1, 0));
        skills.add(new Skills(2, 0));
        skills.add(new Skills(1, 1));
        skills.add(new Skills(1, 2));
        //end of testing lines
    }

    @Override
    public void Update(float deltaTime){
        super.Update(deltaTime);
        Camera.instance.setPosition(xPos-Camera.instance.getWidth()/2,yPos-Camera.instance.getHeight()/2);
        if(animation == null || !animation.isRunning()) {
            Map map = mapHandler.getCurrentMap();

            if(KeyPolling.isDown(KeyCode.I)){
                Main.clearUptadables();
                Main.setScene("/resources/fxml/sceneInventory.fxml");

                return;
            }
            if(KeyPolling.isDown(KeyCode.F)){
                Main.clearUptadables();
                Main.setScene("/resources/fxml/sceneContainer.fxml");
                return;
            }
            if (KeyPolling.isDown(KeyCode.A)) {
                if(!map.getLayer(zPos).getCollisionAtPos(x-1,y)) {
                    animation = new Animation(0.25f, -32, 0);
                    animation.Play(this);
                    x--;
                }
            } else if (KeyPolling.isDown(KeyCode.D)) {
                if(!map.getLayer(zPos).getCollisionAtPos(x+1,y)) {
                    animation = new Animation(0.25f, 32, 0);
                    animation.Play(this);
                    x++;
                }
            } else if (KeyPolling.isDown(KeyCode.S)) {
                if(!map.getLayer(zPos).getCollisionAtPos(x,y+1)) {
                    animation = new Animation(0.25f,0,32);
                    animation.Play(this);
                    y++;
                }
            } else if (KeyPolling.isDown(KeyCode.W)) {
                if(!map.getLayer(zPos).getCollisionAtPos(x,y-1)) {
                    animation = new Animation(0.25f, 0, -32);
                    animation.Play(this);
                    y--;
                }
            }
        }
        //TODO: Handle events
    }

}
