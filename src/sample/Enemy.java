package sample;

import java.io.File;
import java.util.Scanner;

public class Enemy {
    public int id;
    public enum difficulty {NORMAL, EPIC, BOSS};
    public difficulty level;
    public String name;
    public int attack;
    public int defense;
    public int agility;
    public int stamina;
    public int mana;
    public int luck;
    public int HP;
    public int maxHP;
    public int maxMana;
    public int maxStamina;
    public int exp;
    public int gold;
    public Skills enemySkill;

    public Enemy(int id, int level){
        this.id = id;
        if(level == 1){
            this.level = difficulty.NORMAL;
        } else if(level == 2){
            this.level = difficulty.EPIC;
        } else {
            this.level = difficulty.BOSS;
        }
        readStats(id);

        System.out.println("Object has been created");
    }

    public String getPathStats(){
        return "src/resources/textures/Enemies/" + level.toString() + "STATS.txt";
    }
    public String getPathImage(){
        return "src/resources/textures/Enemies/" + level.toString() + id+ ".png";
        //return "src/resources/textures/Enemies/BOSS1.png";
    }
    public void readStats(int id){
        String path = getPathStats();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            while(myReader.hasNextLine()){
                data = myReader.nextLine();
                String[] tmp_tab = data.split(":", 20);
                if(tmp_tab[0].equals(String.valueOf(id))){
                    this.name = tmp_tab[1];
                    this.attack = Integer.parseInt(tmp_tab[2]);
                    this.defense = Integer.parseInt(tmp_tab[3]);
                    this.agility = Integer.parseInt(tmp_tab[4]);
                    this.luck = Integer.parseInt(tmp_tab[5]);
                    this.maxHP = Integer.parseInt(tmp_tab[6]);
                    this.HP = this.maxHP;
                    this.stamina = Integer.parseInt(tmp_tab[7]);
                    this.maxStamina = this.stamina;
                    this.maxMana = Integer.parseInt(tmp_tab[8]);
                    this.mana = this.maxMana;
                    this.enemySkill = new Skills(Integer.parseInt(tmp_tab[9]), 0);
                    this.exp = Integer.parseInt(tmp_tab[10]);
                    this.gold = Integer.parseInt(tmp_tab[11]);
                    return;
                }
            }
        } catch (Exception e){
            System.out.println("File has not been found - "+ this + ".txt");
            e.printStackTrace();
        }
    }
}
