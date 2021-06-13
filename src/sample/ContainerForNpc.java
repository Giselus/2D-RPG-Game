package sample;

import java.util.HashMap;

public class ContainerForNpc{
    public String name;
    public Inventory inventory;
    boolean isShop;

    public static HashMap<String, ContainerForNpc> npcsInventories = new HashMap<>();

    ContainerForNpc(String name,int sizeX, int sizeY, boolean isShop){
        this.name = name;
        this.isShop = isShop;
        inventory = new Inventory(sizeX, sizeY);
        npcsInventories.put(name,this);
    }

    public ContainerForNpc FetchByName(String name){
        if(npcsInventories.containsKey(name))
            return npcsInventories.get(name);
        return null;
    }

    public static void Initialize(){
        new ContainerForNpc("AlchemistShop", 4,4,true);
        new ContainerForNpc("ArmourerShop", 4,4,true);
    }
}
