package sample;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class CharacterManager extends GameObject{

    public static CharacterManager instance;
    int x, y;
    String name;
    int attack;
    int defense;
    int luck;
    int agility;
    int hp;
    Image skin;
    Image legs;
    Image body;
    Image hair;
    public CharacterManager(){
        instance = this;
    }

    public CharacterManager(String name,int attack,int defense,int luck,
            int agility,int hp,Image skin,Image legs,Image body,Image hair,
                            int xPos, int yPos, int zPos, ImageFrame ... img){
        super(xPos * 32,yPos * 32,zPos,img);
        x = xPos;
        y = yPos;
        instance = this;
        this.name=name;
        this.attack=attack;
        this.defense=defense;
        this.luck=luck;
        this.agility=agility;
        this.hp=hp;
        this.skin=skin;
        this.legs=legs;
        this.body=body;
        this.hair=hair;
    }

    @Override
    public void Update(float deltaTime){

        super.Update(deltaTime);
        Camera.instance.setPosition(xPos-Camera.instance.getWidth()/2,yPos-Camera.instance.getHeight()/2);
        if(animation == null || !animation.isRunning()) {
            Map map = mapHandler.getCurrentMap();
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
