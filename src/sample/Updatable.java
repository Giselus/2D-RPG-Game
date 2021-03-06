package sample;

import java.util.ArrayList;
import java.util.HashSet;

public class Updatable {

    public static HashSet<Updatable> updatableList = new HashSet<>();
    public static HashSet<Updatable> newObjects = new HashSet<>();

    private boolean active;

    public Updatable(){
        active = true;
        newObjects.add(this);
    }

    public void setActive(boolean active){
        this.active = active;
        if(active){
            newObjects.add(this);
        }
    }
    public boolean isActive(){
        return  active;
    }
    public void Update(float deltaTime){

    }

    public void LateUpdate(float deltaTime){}

    public static void clearUpdatables(){
        for(Updatable object: newObjects)
            object.setActive(false);
        for(Updatable object :updatableList)
            object.setActive(false);
    }
}
