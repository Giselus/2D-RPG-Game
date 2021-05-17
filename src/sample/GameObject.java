package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.controllers.mainGameController;

import java.util.ArrayList;

public class GameObject extends Updatable{

    public ImageFrame img;
    public float xPos, yPos;
    int zPos;
    public Animation animation;

    public GameObject(){

    }

    public GameObject(ImageFrame img, float xPos, float yPos, int zPos){
        this.img = img;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public void Update(float deltaTime){
        if(animation != null){
            animation.Update(deltaTime);
        }
        RenderingManager.addToRenderQueue(this);
    }

    public void Draw(){
        if(img != null)
            mainGameController.instance.drawImage(img,xPos,yPos);
    }
}
