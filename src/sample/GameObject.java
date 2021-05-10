package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.controllers.mainGameController;

import java.util.ArrayList;

public class GameObject {

    public static ArrayList<GameObject> updatable = new ArrayList<>();

    public ImageFrame img;
    public float xPos, yPos;
    private boolean active;
    public Animation animation;

    public GameObject(){
        active = true;
        updatable.add(this);
    }

    public GameObject(ImageFrame img, float xPos, float yPos){
        this.img = img;
        this.xPos = xPos;
        this.yPos = yPos;
        active = true;
        updatable.add(this);
    }

    private void setActive(boolean active){
        this.active = active;
        if(active == true){
            if(!updatable.contains(this)){
                updatable.add(this);
            }
        }else{
            if(updatable.contains(this)){
                updatable.remove(this);
            }
        }
    }

    public void Update(float deltaTime){
        if(animation != null){
            animation.Update(deltaTime);
        }

        Draw();
    }

    public void Draw(){
        if(img != null)
            mainGameController.instance.drawImage(img,xPos,yPos);
    }
}
