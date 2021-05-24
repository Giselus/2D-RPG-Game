package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Animation {

    public float time;
    public float duration;
    public float imageTime;
    public float imageDuration;
    public boolean continuous;
    private ArrayList<ArrayList<ImageFrame>> images;
    private float xOffset;
    private float yOffset;
    private float xStart, yStart;
    private boolean active;

    private GameObject obj;

    //TODO: In case of bugs, continuous may be helpful

    public Animation(float duration, float xOffset, float yOffset, ArrayList < ImageFrame > ... imgs){
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.duration = duration;
        this.imageDuration = duration;
        this.continuous = false;
        this.images = new ArrayList<>();
        for(ArrayList < ImageFrame > list : imgs){
            ArrayList<ImageFrame> temp = new ArrayList<>();
            for(ImageFrame frame : list ){
                temp.add(frame);
            }
            images.add(temp);
        }
    }

    public void Play(GameObject obj){
        if(!continuous){
            imageTime = 0f;
        }
        active = true;
        xStart = obj.xPos;
        yStart = obj.yPos;
        this.obj = obj;
        time = 0f;
    }

    public void Stop(){
        active = false;
    }

    public void Reset(){
        imageTime = 0f;
    }

    public boolean isRunning(){
        return active;
    }

    void Update(float deltaTime){
        if(!active)
            return;
        time += deltaTime;
        imageTime += deltaTime;
        if (continuous) {
            imageTime %= imageDuration;
            if(images.size() > 0){
                obj.images.clear();
                for(ArrayList<ImageFrame> list: images){
                    obj.images.add(list.get((int)(imageTime/imageDuration * list.size())));
                }
            }
        }else{
            if(imageTime > imageDuration){
                if(images.size() > 0) {
                    obj.images.clear();
                    for(ArrayList<ImageFrame> list: images){
                        obj.images.add(list.get(list.size()-1));
                    }
                }
            }else{
                if(images.size() > 0){
                    obj.images.clear();
                    for(ArrayList<ImageFrame> list: images){
                        obj.images.add(list.get((int)(imageTime/imageDuration * list.size())));
                    }
                }
            }
        }
        if(time > duration) {
            obj.yPos = yStart + yOffset;
            obj.xPos = xStart + xOffset;
            Stop();
        }else{
            obj.xPos = xStart  + (time/duration * xOffset);
            obj.yPos = yStart  + (time/duration * yOffset);
        }
    }
}
