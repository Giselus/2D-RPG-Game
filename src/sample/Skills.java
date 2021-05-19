package sample;

import java.io.File;
import java.util.Scanner;

public class Skills {

    int id;
    skillType myType;
    int costMana;
    int costStamina;
    int damage;
    int plusMana;
    int plusStamina;
    int plusHealth;
    int plusAttack;
    int plusDefense;
    float modifier;
    String nameOfSkill;

    public enum skillType{
        OFFENSIVE, HEAL, BUFF
    }

    public Skills(int id, int id_type){
        this.id = id;
        if(id_type == 0){
            myType = skillType.OFFENSIVE;
        } else if(id_type == 1){
            myType = skillType.HEAL;
        } else if(id_type == 2){
            myType = skillType.BUFF;
        }
        readSkillParameter(id, myType);
    }

    public void readSkillParameter(int id, skillType myTypes){
        String data = returnLine(id, myTypes);
        if(myTypes == skillType.OFFENSIVE){
            String[] tmp_tab = data.split(":", 6);
            nameOfSkill = tmp_tab[1];
            damage = Integer.parseInt(tmp_tab[2]);
            modifier = Float.parseFloat(tmp_tab[3]);
            costStamina = Integer.parseInt(tmp_tab[4]);
            costMana = Integer.parseInt(tmp_tab[5]);
        } else if(myTypes == skillType.HEAL){
            String[] tmp_tab = data.split(":", 7);
            nameOfSkill = tmp_tab[1];
            plusHealth = Integer.parseInt(tmp_tab[2]);
            plusStamina = Integer.parseInt(tmp_tab[3]);
            plusMana = Integer.parseInt(tmp_tab[4]);
            costStamina = Integer.parseInt(tmp_tab[5]);
            costMana = Integer.parseInt(tmp_tab[6]);
        } else if(myTypes == skillType.BUFF){
            String[] tmp_tab = data.split(":", 6);
            nameOfSkill = tmp_tab[1];
            plusAttack = Integer.parseInt(tmp_tab[2]);
            plusDefense = Integer.parseInt(tmp_tab[3]);
            costStamina = Integer.parseInt(tmp_tab[4]);
            costMana = Integer.parseInt(tmp_tab[5]);
        }
    }
    String returnLine(int id, skillType s){
        try {
            File myObj = new File("src/resources/textures/Skills/"+s.toString()+".txt");
            Scanner myReader = new Scanner(myObj);
            int counter = 0;
            String data = "";
            while(myReader.hasNextLine()){
                data = myReader.nextLine();
                if(counter == id){
                    break;
                }
                counter++;
            }
            return data;
        } catch (Exception e){
            System.out.println("File has not been found - "+ s.toString() + ".txt");
            e.printStackTrace();
        }
        return "";
    }

    public int getPlusHealth(){
        return plusHealth;
    }
    public String getNameOfSkill(){
        return nameOfSkill;
    }
    public void setModifier(float modifier) {
        this.modifier = modifier;
    }
    public int getId() {
        return id;
    }
    public skillType getMyType(){
        return myType;
    }
    public int getPlusAttack(){
        return plusAttack;
    }
    public int getDamage(){
        return damage;
    }
    public float getModifier(){
        return modifier;
    }

    @Override
    public String toString(){
        StringBuilder to_return = new StringBuilder();
        if(myType == skillType.OFFENSIVE){
            to_return.append(nameOfSkill).append(" - ").append(myType).append("\n");
            to_return.append("Attack - ").append(damage).append("\n");
        } else if(myType == skillType.HEAL){
            to_return.append(nameOfSkill).append(" - ").append(myType).append("\n");
            to_return.append("Heal - ").append(plusHealth).append("\n");
        } else if(myType == skillType.BUFF){
            to_return.append(nameOfSkill).append(" - ").append(myType).append("\n");
            to_return.append("Bonus attack - ").append(plusAttack).append("\n");
        }
        System.out.println(to_return.toString());
        return to_return.toString();
    }
}
