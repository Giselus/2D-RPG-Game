package sample;

public class ContainerForNpc extends InteractiveObject{
    public Inventory inventory;
    boolean isShop;
    ContainerForNpc(int x_coordinate, int y_coordinate, int sizeX, int sizeY, boolean isShop){
        super(x_coordinate, y_coordinate);
        this.myType = typeOfObject.CONTAINER;
       // this.isShop = isShop;
        inventory = new Inventory(sizeX, sizeY);
    }
}
