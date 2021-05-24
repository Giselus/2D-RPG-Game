package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Items {
    public int id;
    public type myType;
    public enum type{
        //0-Empty, 1-Boots, 2-Armor, 3-Helmet, 4-Weapon-one, 5-weapon-two, 6-trinket, 7-utility
        EMPTY, BOOTS, ARMOR, HELMET, WEAPON_ONE, WEAPON_TWO, TRINKET, UTILITY
    }

    public Items(int types, int id) {
        this.id = id;
        if(types == 0){
            myType = type.EMPTY;
        } else if(types == 1){
            myType = type.BOOTS;
        } else if(types == 2){
            myType = type.ARMOR;
        } else if(types == 3){
            myType = type.HELMET;
        } else if(types == 4){
            myType = type.WEAPON_ONE;
        } else if(types == 5){
            myType = type.WEAPON_TWO;
        } else if(types == 6){
            myType = type.TRINKET;
        } else if(types == 7){
            myType = type.UTILITY;
        }
    }
    @Override
    public String toString(){
        return myType.toString() + this.id;
    }

    public String getPath(){
        return "/resources/textures/Items/" + myType.toString() + id + ".png";
    }

    public ImageView getImageView(){
        String path = getPath();
        Image image = new Image((Objects.requireNonNull(getClass().getResource(path))).toString());
        ImageView imageView = new ImageView(image);
        if(myType == type.ARMOR || myType == type.BOOTS || myType == type.HELMET){
            imageView.setViewport(new Rectangle2D(64,64*10,64,64));
        }
        return imageView;
    }
}
