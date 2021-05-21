package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.CharacterManager;
import sample.Skills;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ControllerFight {



    int roundCounter = 1;
    boolean endOfFight = false;
    //we have to read this data from different class, hard coded values right now
    int playerMana = 100;
    int playerMaxMana = 100;
    int playerStamina = 100;
    int playerMaxStamina = 100;
    int playerHP = 100;
    int playerMaxHP = 100;
    int enemyHP = 100;
    int enemyMaxHP = 100;
    int attack = 10;

    @FXML public Button useSkill;
    @FXML public Button chosenSkill;
    @FXML public TextArea skillDesc;
    @FXML public Text mobHP;
    @FXML public Text heroHP;
    @FXML public Rectangle hpBarEnemy;
    @FXML public Rectangle hpBarFillEnemy;
    @FXML public Rectangle hpBar;
    @FXML public Rectangle hpBarFill;
    @FXML public TextArea fightHistory;
    @FXML public ImageView skill_one;
    @FXML public ImageView skill_two;
    @FXML public ImageView skill_three;
    @FXML public ImageView skill_four;

    public ArrayList<Skills> skillsList;
    public static Skills tmpSkill;

    public void initialize(){
        if(CharacterManager.instance == null){
            new CharacterManager();
        }
        skillsList = new ArrayList<>(4);
        for(int i=0; i<4; i++){
            skillsList.add(CharacterManager.instance.skills.get(i));
        }
        skill_one.setImage(getImageForSkill(skillsList.get(0)));
        skill_two.setImage(getImageForSkill(skillsList.get(1)));
        skill_three.setImage(getImageForSkill(skillsList.get(2)));
        skill_four.setImage(getImageForSkill(skillsList.get(3)));
    }

    Image getImageForSkill(Skills skill){
        File file = new File(skill.getPath());
        return new Image(file.toURI().toString());
    }
    void actionDependsOnSkillType(Skills s){
        if(s.getMyType() == Skills.skillType.OFFENSIVE){
            offensiveSkill(s);
        } else if(s.getMyType() == Skills.skillType.HEAL) {
            healSkill(s);
        } else if(s.getMyType() == Skills.skillType.BUFF){
            buffSkill(s);
        }
        tmpSkill = null;
    }
    void takeSkill(Skills s){
        skillDesc.setText("");
        tmpSkill = s;
        skillDesc.appendText(tmpSkill.toString());
    }
    void offensiveSkill(Skills s){
        int countDamage = (int) (s.getDamage()+attack*s.getModifier());
        int littleRandomness = ThreadLocalRandom.current().nextInt(-countDamage/10, countDamage/10+1);
        countDamage += littleRandomness;
        fightHistory.appendText(damageToString(s, countDamage));
        if(checkEnemyHP(countDamage)){
            fightHistory.appendText(showEnemyHp());
        } else {
            endOfFight = true;
            endBattle();
            fightHistory.appendText(winningMessage());
        }
    }
    void buffSkill(Skills s){
        attack += s.getPlusAttack();
        fightHistory.appendText(buffsToString(s));
    }
    void healSkill(Skills s){
        playerHP += s.getPlusHealth();
        if(playerHP > playerMaxHP){
            playerHP = playerMaxHP;
        }
        fightHistory.appendText(healAmount(s));
        hpBarFill.setWidth((double)(200/playerMaxHP) * playerHP);
        fightHistory.appendText(showPlayerHp());
    }

    String healAmount(Skills s){
        return "You are using " + s.getNameOfSkill() + " and feel that your wounds are healing. You restore "+ s.getPlusHealth() +" HP.\n";
    }
    String buffsToString(Skills s){
        return "You are using "+ s.getNameOfSkill() +" and feel stronger. Your attack increases.\n";
    }
    String damageToString(Skills s, int damage){
        return "You are using " + s.getNameOfSkill() + " and you are dealing " + damage +" damage.\n";
    }
    String showEnemyHp(){
        return "Your enemy has " + enemyHP + " HP left.\n";
    }
    String showPlayerHp(){
        return "You have " + playerHP + " HP left.\n";
    }
    String winningMessage(){
        return "You won! Your enemy is dead.\n";
    }

    boolean checkEnemyHP(int dmg){
        enemyHP -= dmg;
        mobHP.setText(enemyHP+"/"+enemyMaxHP);
        hpBarFillEnemy.setWidth((double)(200/enemyMaxHP) * enemyHP);
        return enemyHP > 0;
    }

    boolean checkPlayerHP(int dmg){
        playerHP -= dmg;
        heroHP.setText(playerHP+"/"+playerMaxHP);
        hpBarFill.setWidth((double)(200/playerMaxHP) * playerHP);
        return playerHP > 0;
    }
    String enemyAttack(){
        return "Enemy uses fireball and deals 25 damage.\n";
    }

    void endBattle(){
        skill_one.setDisable(true);
        skill_two.setDisable(true);
        skill_three.setDisable(true);
        skill_four.setDisable(true);
    }
    public void one() {
        takeSkill(skillsList.get(0));
    }
    public void two() {
        takeSkill(skillsList.get(1));
    }
    public void three() {
        takeSkill(skillsList.get(2));
    }
    public void four(){
        takeSkill(skillsList.get(3));
    }

    @FXML
    void useSkillNow(){
        if(tmpSkill == null){
            return;
        }
        actionDependsOnSkillType(tmpSkill);
        if(!endOfFight){
            endRound();
        }
        skillDesc.setText("");
    }
    public void endRound() {
        fightHistory.appendText("\n");
        fightHistory.appendText("Round " + roundCounter + " - Enemy turn\n");
        fightHistory.appendText(enemyAttack());
        if(checkPlayerHP(25+ ThreadLocalRandom.current().nextInt(-5, 6))){
            fightHistory.appendText(showPlayerHp());
        } else{
            fightHistory.appendText("You lost!\n");
            endBattle();
            endOfFight = true;
            return;
        }
        fightHistory.appendText("\n");
        roundCounter++;
        fightHistory.appendText("Round " + roundCounter + " - Player turn\n");
    }
}
