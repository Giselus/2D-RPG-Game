package sample;

public class InteractiveObject {
    int x_coordinate;
    int y_coordinate;
    typeOfObject myType;
    public enum typeOfObject{
        CONTAINER, NPC, ENEMY
    }
    InteractiveObject(int x_coordinate, int y_coordinate){
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }
}
