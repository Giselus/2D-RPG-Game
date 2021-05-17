package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.Skills;
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

    @FXML
    public Button useSkill;
    public Button chosenSkill;
    public TextArea skillDesc;
    public Text mobHP;
    public Text heroHP;
    public Rectangle hpBarEnemy;
    public Rectangle hpBarFillEnemy;
    public Rectangle hpBar;
    public Rectangle hpBarFill;
    public Button skillOne;
    public TextArea fightHistory;
    public Button skillTwo;
    public Button skillThree;
    public Button skillFour;

    public Skills skill1 = new Skills(1, 0);
    public Skills skill2 = new Skills(2, 0);
    public Skills skill3 = new Skills(1, 1);
    public Skills skill4 = new Skills(1, 2);

    public static Skills tmpSkill;

    void actionDependsOnSkillType(Skills s){
        if(s.getMyType() == Skills.skillType.OFFENSIVE){
            offensiveSkill(s);
        } else if(s.getMyType() == Skills.skillType.HEAL) {
            healSkill(s);
        } else if(s.getMyType() == Skills.skillType.BUFF){
            buffSkill(s);
        }
    }
    void takeSkill(Skills s){
        skillDesc.setText("");
        tmpSkill = s;
        skillDesc.appendText(tmpSkill.toString());
    }
    void offensiveSkill(Skills s){
        int countDamage = (int) (s.getDamage()+attack*s.getModifier());
        int littleRandomness = ThreadLocalRandom.current().nextInt(-countDamage/10, countDamage/10);
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
        attack += 10;
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
        skillOne.setDisable(true);
        skillTwo.setDisable(true);
        skillThree.setDisable(true);
        skillFour.setDisable(true);
    }
    public void one() {
        takeSkill(skill1);
    }
    public void two() {
        takeSkill(skill2);
    }
    public void three() {
        takeSkill(skill3);
    }
    public void four(){
        takeSkill(skill4);
    }

    @FXML
    void useSkillNow(){
        if(tmpSkill == null){
            return;
        }
        actionDependsOnSkillType(tmpSkill);
        endRound();
        skillDesc.setText("");
    }
    public void endRound() {
        fightHistory.appendText("\n");
        fightHistory.appendText("Round " + roundCounter + " - Enemy turn\n");
        fightHistory.appendText(enemyAttack());
        if(checkPlayerHP(25)){
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
