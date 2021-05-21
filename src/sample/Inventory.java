package sample;

import java.util.ArrayList;

public class Inventory {
    private int invSizeX;
    private int invSizeY;
    private int quantity;
    private ArrayList<Items> itemsList;

    public Inventory(int x, int y){
        invSizeX = x;
        invSizeY = y;
        quantity = 0;
        itemsList = new ArrayList<>();
        for(int i=0; i<invSizeY*invSizeX; i++){
            itemsList.add(new Items(0, 0));
        }
    }
    public ArrayList<Items> getAllItems(){
        return itemsList;
    }

    public void addItem(Items items){
        if(quantity >= invSizeX*invSizeY){
            return;
        }
        quantity++;
        boolean flag = false;
        for(int i=0; i<invSizeX; i++){
            for(int j=0; j<invSizeY; j++){
                if(itemsList.get(invSizeX*i + j).myType == Items.type.EMPTY){
                    itemsList.set(invSizeX*i + j, items);
                    flag = true;
                    break;
                }
            }
            if(flag){
                break;
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
    public void setAllItemsList(ArrayList<ArrayList<Items>> arg){
        for(int i=0; i<arg.size(); i++){
            for(int j=0; j<arg.get(0).size(); j++){
                itemsList.set(invSizeX*i+j, arg.get(i).get(j));
            }
        }
    }
}
