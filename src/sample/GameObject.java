package sample;

import sample.controllers.mainGameController;

import java.util.ArrayList;

public class GameObject extends Updatable{

    public ArrayList<ImageFrame> images;
    public float xPos, yPos;
    int x,y;
    int z;
    public Animation animation;

    public GameObject(){

    }

    public GameObject(int x, int y, int z, ImageFrame ... img){
        images = new ArrayList<>();
        for(ImageFrame image : img){
            images.add(image);
        }
        this.x = x;
        this.y = y;
        this.xPos = x * 32;
        this.yPos = y * 32;
        this.z = z;
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
