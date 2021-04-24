package sample;

public class Items {
    public int id;
    public type myType;

    public enum type{
        WEAPON_ONE, WEAPON_TWO, ARMOR, HELMET, UTILITY, BOOTS, TRINKET, EMPTY
    }

    public Items(int id) {
        this.id = id;
        if(id == 8) {
            myType = type.UTILITY;
        } else if(id == 1){
            myType = type.BOOTS;
        } else if(id == 2){
            myType = type.ARMOR;
        } else if(id == 3){
            myType = type.HELMET;
        } else if(id == 4){
            myType = type.WEAPON_ONE;
        } else if(id == 5){
            myType = type.WEAPON_TWO;
        } else if(id == 6 || id == 7){
            myType = type.TRINKET;
        } else {
            myType = type.EMPTY;
        }
    }
    @Override
    public String toString(){
        return "My name is " + this.id;
    }
}
