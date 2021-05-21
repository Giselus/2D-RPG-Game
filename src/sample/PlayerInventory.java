package sample;

import java.util.ArrayList;

public class PlayerInventory extends Inventory{

    private ArrayList<Items> equippedItemsList;
    public PlayerInventory(int x, int y) {
        super(x, y);
        equippedItemsList = new ArrayList<>();
        for(int i=0; i<7; i++){
            equippedItemsList.add(new Items(0, 0));
        }
    }
    public ArrayList<Items> getEquippedItemsList(){
        return equippedItemsList;
    }
    public void setEquippedItem(int index, Items items){
        equippedItemsList.set(index, items);
    }
    public void setAllEquippedItems(ArrayList<Items> arg){
        for(int i=0; i<7; i++){
            equippedItemsList.set(i, arg.get(i));
        }
    }
}
