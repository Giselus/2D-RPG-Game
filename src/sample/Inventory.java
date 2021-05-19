package sample;

import java.util.ArrayList;

public class Inventory {
    private int invSizeX;
    private int invSizeY;
    private int quantity;
    private ArrayList<Items> itemsList;
    private ArrayList<Items> equippedItemsList;

    public Inventory(){
        invSizeX = 4;
        invSizeY = 4;
        quantity = 0;
        itemsList = new ArrayList<>();
        for(int i=0; i<16; i++){
            itemsList.add(new Items(0, 0));
        }
        equippedItemsList = new ArrayList<>();
        for(int i=0; i<7; i++){
            equippedItemsList.add(new Items(0, 0));
        }
    }
    public ArrayList<Items> getAllItems(){
        return itemsList;
    }
    public ArrayList<Items> getEquippedItemsList(){
        return equippedItemsList;
    }

    public void addItem(Items items){
        if(quantity >= 16){
            return;
        }
        quantity++;
        for(int i=0; i<invSizeX; i++){
            for(int j=0; j<invSizeY; j++){
                if(itemsList.get(3*i + j).myType == Items.type.EMPTY){
                    itemsList.set(3*i+j, items);
                    return;
                }
            }
        }
    }
    public void equipItem(){
        quantity--;
    }
    public void removeItem(int index){
        quantity--;
        itemsList.set(index, new Items(0, 0));
    }
    public void setEquippedItem(int index, Items items){
        equippedItemsList.set(index, items);
    }
    public void setAllItemsList(ArrayList<ArrayList<Items>> arg){
        for(int i=0; i<arg.size(); i++){
            for(int j=0; j<arg.get(0).size(); j++){
                itemsList.set(3*i+j, arg.get(i).get(j));
            }
        }
    }
    public void setAllEquippedItems(ArrayList<Items> arg){
        for(int i=0; i<7; i++){
            equippedItemsList.set(i, arg.get(i));
        }
    }
}
