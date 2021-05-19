package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Animation {



    public float speed;
    public float time;
    public float duration;
    private ArrayList<ArrayList<ImageFrame>> images;
    private float xOffset;
    private float yOffset;
    private float xStart, yStart;
    private boolean active;

    private GameObject obj;

    public Animation(float duration, float xOffset, float yOffset, ArrayList < ImageFrame > ... imgs){
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.duration = duration;
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

            if(images.size() > 0) {
                obj.images.clear();
                for(ArrayList<ImageFrame> list: images){
                    obj.images.add(list.get(list.size()-1));
                }
            }
            Stop();
        }else{
            obj.xPos = xStart  + (time/duration * xOffset);
            obj.yPos = yStart  + (time/duration * yOffset);

            if(images.size() > 0){
                obj.images.clear();
                for(ArrayList<ImageFrame> list: images){
                    obj.images.add(list.get((int)(time/duration * list.size())));
                }
            }
        }
    }
}
