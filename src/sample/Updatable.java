package sample;

import sample.controllers.mainGameController;

import java.util.ArrayList;

public class Updatable {

    public static ArrayList<Updatable> updatableList = new ArrayList<>();

    private boolean active;

    public Updatable(){
        active = true;
        updatableList.add(this);
    }

    private void setActive(boolean active){
        this.active = active;
        if(active == true){
            if(!updatableList.contains(this)){
                updatableList.add(this);
            }
        }else{
            if(updatableList.contains(this)){
                updatableList.remove(this);
            }
        }
    }
    public void Update(float deltaTime){

    }

}
