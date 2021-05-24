package sample;

import java.util.ArrayList;

public class InventoryPlayer extends Inventory{

    private ArrayList<Items> equippedItemsList;
    public InventoryPlayer(int x, int y) {
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
    public static void swapToWear(int x, ArrayList<Items> equipmentView,ArrayList<ArrayList<Items>> itemsView,
                           TemporaryChosenContainer temporaryChosen){
        if(equipmentView.get(x).myType == Items.type.EMPTY){
            itemsView.get(temporaryChosen.cordX).set(temporaryChosen.cordY, new Items(0, 0));
            CharacterManager.instance.inventory.equipItem();
            equipmentView.set(x, temporaryChosen.item);
        } else {
            Items swap = equipmentView.get(x);
            equipmentView.set(x, temporaryChosen.item);
            itemsView.get(temporaryChosen.cordX).set(temporaryChosen.cordY, swap);
        }
        temporaryChosen.clearHolder();
    }
    public static void quickUseOfItem(int x, int y, ArrayList<Items> equipmentView,ArrayList<ArrayList<Items>> itemsView,
                                      TemporaryChosenContainer temporaryChosen){
        if(temporaryChosen.hasItems){
            temporaryChosen.clearHolder();
            return;
        }
        Items tempItem = itemsView.get(x).get(y);
        int tmpValue;
        if(tempItem.myType == Items.type.EMPTY){
            return;
        }
        if(tempItem.myType == Items.type.BOOTS){
            tmpValue = 0;
        } else if(tempItem.myType == Items.type.ARMOR){
            tmpValue = 1;
        } else if(tempItem.myType == Items.type.HELMET){
            tmpValue = 2;
        } else if(tempItem.myType == Items.type.WEAPON_ONE){
            tmpValue = 3;
        } else if(tempItem.myType == Items.type.WEAPON_TWO){
            tmpValue = 4;
        } else if (tempItem.myType == Items.type.TRINKET){
            if(equipmentView.get(5).myType == Items.type.EMPTY){
                tmpValue = 5;
            } else {
                tmpValue = 6;
            }
        } else {
            CharacterManager.instance.current_hp += 50;
            CharacterManager.instance.current_hp = Math.min(CharacterManager.instance.current_hp, CharacterManager.instance.hp);
            itemsView.get(x).set(y, new Items(0, 0));
            return;
        }
        if(equipmentView.get(tmpValue).myType == Items.type.EMPTY){
            itemsView.get(x).set(y, new Items(0, 0));
        } else {
            itemsView.get(x).set(y, equipmentView.get(tmpValue));
        }
        equipmentView.set(tmpValue, tempItem);
    }
    public static void pickItemFromEquipment(int x, ArrayList<Items> equipmentView, TemporaryChosenContainer temporaryChosen){
        if(equipmentView.get(x).myType == Items.type.EMPTY){
            return;
        }
        temporaryChosen.pickWearing(x, equipmentView);
    }
    public static void checkSlotProperties(int x, int y, ArrayList<ArrayList<Items>> itemsView,
                                           TemporaryChosenContainer temporaryChosen, ArrayList<Items> equipmentView){
        if(!temporaryChosen.hasItems){
            if(itemsView.get(x).get(y).myType != Items.type.EMPTY){
                temporaryChosen.pickEquipment(x, y, itemsView);
            }
            return;
        }
        if(temporaryChosen.isWearing){
            if(itemsView.get(x).get(y).myType == temporaryChosen.item.myType){
                equipmentView.set(temporaryChosen.equipmentId, itemsView.get(x).get(y));
                itemsView.get(x).set(y, temporaryChosen.item);
            } else if(itemsView.get(x).get(y).myType == Items.type.EMPTY){
                equipmentView.set(temporaryChosen.equipmentId, new Items(0, 0));
                itemsView.get(x).set(y, temporaryChosen.item);
            }
        } else {
            if (itemsView.get(x).get(y).myType != Items.type.EMPTY) {
                itemsView.get(temporaryChosen.cordX).set(temporaryChosen.cordY, itemsView.get(x).get(y));
            } else {
                itemsView.get(temporaryChosen.cordX).set(temporaryChosen.cordY, new Items(0, 0));
            }
            itemsView.get(x).set(y, temporaryChosen.item);
        }
        temporaryChosen.clearHolder();
    }
}
