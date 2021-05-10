package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Animation {



    public float speed;
    public float time;
    public float duration;
    private ArrayList<ImageFrame> images;
    private float xOffset;
    private float yOffset;
    private float xStart, yStart;
    private boolean active;

    private GameObject obj;

    public Animation(float duration, float xOffset, float yOffset, ImageFrame ... images){
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.duration = duration;
        this.images = new ArrayList<>();
        for(ImageFrame frame : images){
            this.images.add(frame);
        }
    }

    public void Play(GameObject obj){
        active = true;
        xStart = obj.xPos;
        yStart = obj.yPos;
        this.obj = obj;
        time = 0;
    }

    public void Stop(){
        active = false;
    }

    public boolean isRunning(){
        return active;
    }

    void Update(float deltaTime){
        if(!active)
            return;
        time += deltaTime;
        if(time > duration) {
            obj.yPos = yStart + yOffset;
            obj.xPos = xStart + xOffset;

            obj.img = images.get(images.size()-1);

            Stop();
        }else{
            obj.xPos = xStart  + (time/duration * xOffset);
            obj.yPos = yStart  + (time/duration * yOffset);

            obj.img = images.get( (int)(time/duration * images.size()));
        }
    }
}
