package sample;

import javafx.scene.Parent;
import sample.controllers.ControllerContainer;

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
    public void setItem(Items items, int x, int y){
        itemsList.set(invSizeX*x + y, items);
    }
    public Items getItem(int x, int y){
        return itemsList.get(invSizeX*x + y);
    }
    public static void swapItemsBetweenInventories(Inventory firstInv, Inventory secondInv, int x1, int y1, int x2, int y2){
        Items itemsOne = firstInv.getItem(x1, y1);
        Items itemsTwo = secondInv.getItem(x2, y2);
        firstInv.setItem(itemsTwo, x1, y1);
        secondInv.setItem(itemsOne, x2, y2);
    }
    public int countItems(){
        int result = 0;
        for(int i=0; i<invSizeX*invSizeY; i++){
            if(itemsList.get(i).myType != Items.type.EMPTY){
                result += 1;
            }
        }
        return result;
    }
    public static void checkSlotProperties(int x, int y, ArrayList<ArrayList<Items>> itemsView, TemporaryChosenContainer temporaryChosen){
        if(!temporaryChosen.hasItems || ControllerContainer.swapChest.isShop){
            if(itemsView.get(x).get(y).myType != Items.type.EMPTY){
                if(x >= 4){
                    temporaryChosen.pickFromShop(x, y, itemsView);
                } else {
                    temporaryChosen.pickEquipment(x, y, itemsView);
                }
            }
            return;
        }
        if (itemsView.get(x).get(y).myType != Items.type.EMPTY) {
            itemsView.get(temporaryChosen.cordX).set(temporaryChosen.cordY, itemsView.get(x).get(y));
        } else {
            itemsView.get(temporaryChosen.cordX).set(temporaryChosen.cordY, new Items(0, 0));
        }
        itemsView.get(x).set(y, temporaryChosen.item);
        temporaryChosen.clearHolder();
        System.out.println("C");
    }
    public static class TemporaryChosenContainer{
        public Integer cordX;
        public Integer cordY;
        public boolean hasItems;
        public boolean isWearing;
        public Integer equipmentId;
        public Items item;
        public boolean fromShop;
        public TemporaryChosenContainer(){
            hasItems = false;
            isWearing = false;
            item = new Items(0, 0);
        }
        public void pickWearing(int x, ArrayList<Items> arg){
            hasItems = true;
            isWearing = true;
            equipmentId = x;
            item = arg.get(x);
        }
        public void pickEquipment(int x, int y, ArrayList<ArrayList<Items>> arg){
            hasItems = true;
            isWearing = false;
            cordX = x;
            cordY = y;
            item = arg.get(x).get(y);
            fromShop = false;
        }
        public void pickFromShop(int x, int y, ArrayList<ArrayList<Items>> arg){
            hasItems = true;
            isWearing = false;
            cordX = x;
            cordY = y;
            item = arg.get(x).get(y);
            fromShop = true;
        }
        public void clearHolder(){
            hasItems = false;
            isWearing = false;
            item = new Items(0, 0);
        }
    }
}
