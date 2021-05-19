package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.controllers.mainGameController;

import java.util.ArrayList;

public class GameObject extends Updatable{

    public ArrayList<ImageFrame> images;
    public float xPos, yPos;
    int zPos;
    public Animation animation;

    public GameObject(){

    }

    public GameObject(float xPos, float yPos, int zPos, ImageFrame ... img){
        images = new ArrayList<>();
        for(ImageFrame image : img){
            images.add(image);
        }
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public void Update(float deltaTime){
        if(animation != null){
            animation.Update(deltaTime);
        }
        RenderingManager.instance.addToRenderQueue(this);
    }

    public void Draw(){
        if(images != null){
            for(ImageFrame img: images){
                mainGameController.instance.drawImage(img,xPos,yPos);
            }
        }
    }
}
