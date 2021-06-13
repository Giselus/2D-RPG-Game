package sample;

import java.util.HashMap;

public class ContainerForNpc{
    public String name;
    public Inventory inventory;
    public boolean isShop;

    public static HashMap<String, ContainerForNpc> npcsInventories = new HashMap<>();

    ContainerForNpc(String name,int sizeX, int sizeY, boolean isShop){
        this.name = name;
        this.isShop = isShop;
        inventory = new Inventory(sizeX, sizeY);
        npcsInventories.put(name,this);
    }

    public static ContainerForNpc FetchByName(String name){
        if(npcsInventories.containsKey(name))
            return npcsInventories.get(name);
        return null;
    }

    public static void Initialize(){
        new ContainerForNpc("AlchemistShop", 4,4,true);
        new ContainerForNpc("ArmourerShop", 4,4,true);
        new ContainerForNpc("ChestInFarm", 4,4,false);
        new ContainerForNpc("ChestReward", 4, 4,false);
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 0));
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 0));
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 0));
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 0));
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 1));
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 1));
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 1));
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 1));
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 2));
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 2));
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 2));
        npcsInventories.get("AlchemistShop").inventory.addItem(new Items(7, 2));

        npcsInventories.get("ArmourerShop").inventory.addItem(new Items(1, 2));
        npcsInventories.get("ArmourerShop").inventory.addItem(new Items(2, 1));
        npcsInventories.get("ArmourerShop").inventory.addItem(new Items(3, 0));
        npcsInventories.get("ArmourerShop").inventory.addItem(new Items(4, 1));
        npcsInventories.get("ArmourerShop").inventory.addItem(new Items(5, 0));
        npcsInventories.get("ArmourerShop").inventory.addItem(new Items(6, 0));
        npcsInventories.get("ArmourerShop").inventory.addItem(new Items(6, 1));
        npcsInventories.get("ArmourerShop").inventory.addItem(new Items(1, 0));
        npcsInventories.get("ArmourerShop").inventory.addItem(new Items(2, 2));
        npcsInventories.get("ArmourerShop").inventory.addItem(new Items(3, 2));
        npcsInventories.get("ArmourerShop").inventory.addItem(new Items(5, 2));

        npcsInventories.get("ChestReward").inventory.addItem(new Items(1, 3));
        npcsInventories.get("ChestReward").inventory.addItem(new Items(2, 0));
        npcsInventories.get("ChestReward").inventory.addItem(new Items(3, 1));
        npcsInventories.get("ChestReward").inventory.addItem(new Items(5, 1));

        npcsInventories.get("ChestInFarm").inventory.addItem(new Items(1, 1));
        npcsInventories.get("ChestInFarm").inventory.addItem(new Items(2, 3));
        npcsInventories.get("ChestInFarm").inventory.addItem(new Items(3, 3));
        npcsInventories.get("ChestInFarm").inventory.addItem(new Items(4, 0));
    }
}
