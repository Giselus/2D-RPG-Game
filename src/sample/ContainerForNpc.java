package sample;

public class ContainerForNpc extends InteractiveObject{

    Inventory inventory;
    ContainerForNpc(int x_coordinate, int y_coordinate, int sizeX, int sizeY){
        super(x_coordinate, y_coordinate);
        this.myType = typeOfObject.CONTAINER;
        inventory = new Inventory(sizeX, sizeY);
    }
}
