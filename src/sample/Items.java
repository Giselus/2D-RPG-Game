package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class Items {
    public int id;
    public type myType;
    public enum type{
        //0-Empty, 1-Boots, 2-Armor, 3-Helmet, 4-Weapon-one, 5-weapon-two, 6-trinket, 7-utility
        EMPTY, BOOTS, ARMOR, HELMET, WEAPON_ONE, WEAPON_TWO, TRINKET, UTILITY
    }
    public itemStats myStatistics;

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
        myStatistics = new itemStats(types, id);
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

    public class itemStats{
        public String name;
        public int attack;
        public int defense;
        public int luck;
        public int agility;
        public int hp;
        public int mana;
        public int stamina;
        public itemStats(int x, int y){
            String data = findLine(x, y);
            if (data.equals("")){
                return;
            }
            String[] tab_data = data.split(":", 11);
            this.name = tab_data[2];
            this.attack = Integer.parseInt(tab_data[3]);
            this.defense = Integer.parseInt(tab_data[4]);
            this.luck = Integer.parseInt(tab_data[5]);
            this.agility = Integer.parseInt(tab_data[6]);
            this.hp = Integer.parseInt(tab_data[7]);
            this.mana = Integer.parseInt(tab_data[8]);
            this.stamina = Integer.parseInt(tab_data[9]);
        }

        String findLine(int x, int y){
            try {
                File myObj = new File("src/resources/textures/Items/itemsDB.txt");
                Scanner myReader = new Scanner(myObj);
                String data = myReader.nextLine();
                while(myReader.hasNextLine()){
                    data = myReader.nextLine();
                    String[] tmp_tab = data.split(":", 20);
                    if(Integer.parseInt(tmp_tab[0]) == x && Integer.parseInt(tmp_tab[1])==y){
                        return data;
                    }
                }
            } catch (Exception e){
                System.out.println("File has not been found - "+ this.toString() + ".txt");
                e.printStackTrace();
            }
            return "";
        }

        @Override
        public String toString(){
            StringBuilder result=new StringBuilder();
            if(!name.equals("EMPTY")){
                result.append(name).append("\n");
            }
            if(attack!=0){
                result.append("Attack: ").append(attack).append("\n");
            }
            if(defense!=0){
                result.append("Defense: ").append(defense).append("\n");
            }
            if(luck!=0){
                result.append("Luck: ").append(luck).append("\n");
            }
            if(agility!=0){
                result.append("Agility: ").append(agility).append("\n");
            }
            if(hp!=0){
                result.append("Health points: ").append(hp).append("\n");
            }
            if(mana!=0){
                result.append("Mana: ").append(mana).append("\n");
            }
            if(stamina!=0){
                result.append("Stamina: ").append(stamina).append("\n");
            }
            return result.toString();

        }
    }
}
