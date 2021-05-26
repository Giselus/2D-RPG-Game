package sample.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.CharacterManager;
import sample.Combat;
import sample.Main;
import sample.Skills;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerFight {

    int roundCounter = 1;
    boolean endOfFight = false;
    boolean wonFight;
    Combat.combatStats player;
    Combat.combatStats enemy;
    @FXML public AnchorPane anchorPane;
    @FXML public Button exitButton;
    @FXML public Button useSkill;
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
    @FXML public ImageView skill_rest;
    @FXML public Text staminaBar;
    @FXML public Text manaBar;
    @FXML public Text attackBar;
    @FXML public Text defenseBar;

    public ArrayList<Skills> skillsList;
    public static Skills tmpSkill;

    @FXML public void initialize(){
        exitButton.setDisable(true);
        skillsList = new ArrayList<>(4);
        for(int i=0; i<4; i++){
            skillsList.add(CharacterManager.instance.skills.get(i));
        }
        skill_one.setImage(Skills.getImageForSkill(skillsList.get(0)));
        skill_two.setImage(Skills.getImageForSkill(skillsList.get(1)));
        skill_three.setImage(Skills.getImageForSkill(skillsList.get(2)));
        skill_four.setImage(Skills.getImageForSkill(skillsList.get(3)));
        skill_rest.setImage(Skills.getImageForSkill(new Skills(2, 1)));
        addPlayer();
        player = new Combat.combatStats(true);
        enemy = new Combat.combatStats();
        checkEnemyHP(0);
        checkPlayerHP(0);
        updateStats();
        fightHistory.setEditable(false);
    }

    public void updateStats(){
        staminaBar.setText("Stamina: "+ player.stamina + "/" + player.maxStamina);
        manaBar.setText("Mana: "+player.mana + "/" + player.maxMana);
        attackBar.setText("Attack: "+player.attack);
        defenseBar.setText("Defense: "+player.defense);
    }
    public void addPlayer(){
        addImage(CharacterManager.instance.skin);
        addImage(CharacterManager.instance.legs);
        if(CharacterManager.instance.hasHelmet){
            addImageView(CharacterManager.instance.helmetOn.getImageView());
        } else {
            addImage(CharacterManager.instance.hair);
        }
        if(CharacterManager.instance.hasArmor){
            addImageView(CharacterManager.instance.armorOn.getImageView());
        } else {
            addImage(CharacterManager.instance.body);
        }
        if(CharacterManager.instance.hasBoots){
            addImageView(CharacterManager.instance.bootsOn.getImageView());
        }
    }
    public void addImage(Image image){
        ImageView imageViewSkin=new ImageView(image);
        imageViewSkin.setViewport(new Rectangle2D(64,64*10,64,64));
        imageViewSkin.setX(390);
        imageViewSkin.setY(300);
        anchorPane.getChildren().add(imageViewSkin);
    }
    public void addImageView(ImageView imageViewSkin){
        imageViewSkin.setX(390);
        imageViewSkin.setY(300);
        anchorPane.getChildren().add(imageViewSkin);
    }

    void actionDependsOnSkillType(Skills s){
        if(s.getMyType() == Skills.skillType.OFFENSIVE){
            offensiveSkill(s, player, enemy);
        } else if(s.getMyType() == Skills.skillType.HEAL) {
            healSkill(s, player);
        } else if(s.getMyType() == Skills.skillType.BUFF){
            buffSkill(s, player);
        }
        tmpSkill = null;
    }
    void takeSkill(Skills s){
        //skillDesc.setText("");
        tmpSkill = s;
        //skillDesc.appendText(tmpSkill.toString());
    }
    void offensiveSkill(Skills s, Combat.combatStats user, Combat.combatStats def) {
        int countDamage = Combat.countDamage(user.attack, user.luck, def.defense, s);
        if(Combat.hitLanded(user.agility, def.agility)){
            fightHistory.appendText(Combat.damageToString(s, countDamage));
            if(checkEnemyHP(countDamage)){
                fightHistory.appendText(Combat.showEnemyHp(enemy.HP));
            } else {
                endOfFight = true;
                wonFight = true;
                endBattle();
                fightHistory.appendText(Combat.winningMessage());
            }
        } else {
            fightHistory.appendText("You missed!\n");
        }
    }
    void enemyOffensiveSkill(Skills s, Combat.combatStats user, Combat.combatStats def) {
        int countDamage = Combat.countDamage(user.attack, user.luck, def.defense, s);
        if(Combat.hitLanded(user.agility, def.agility)){
            fightHistory.appendText(Combat.EdamageToString(s, countDamage));
            if(checkPlayerHP(countDamage)){
                fightHistory.appendText(Combat.showPlayerHp(player.HP));
            } else {
                endOfFight = true;
                wonFight = false;
                endBattle();
                fightHistory.appendText(Combat.losingMessage());
            }
        } else {
            fightHistory.appendText("Enemy missed!\n");
        }
    }
    void buffSkill(Skills s, Combat.combatStats user){
        user.attack += s.getPlusAttack();
        fightHistory.appendText(Combat.buffsToString(s));
    }
    void healSkill(Skills s, Combat.combatStats user){
        user.HP += s.getPlusHealth();
        user.stamina += s.getPlusStamina();
        user.mana += s.getPlusMana();
        user.HP = Math.min(user.HP, user.maxHP);
        user.stamina = Math.min(user.stamina, user.maxStamina);
        user.mana = Math.min(user.mana, user.maxMana);
        fightHistory.appendText(Combat.healAmount(s));
        checkPlayerHP(0);
        fightHistory.appendText(Combat.showPlayerHp(player.HP));
        System.out.println(s.getCostStamina());
    }
    boolean checkEnemyHP(int dmg){
        enemy.HP -= dmg;
        mobHP.setText(enemy.HP+"/"+enemy.maxHP);
        hpBarFillEnemy.setWidth((double)(200f/enemy.maxHP) * enemy.HP);
        return enemy.HP > 0;
    }
    boolean checkPlayerHP(int dmg){
        player.HP -= dmg;
        heroHP.setText(player.HP+"/"+player.maxHP);
        hpBarFill.setWidth((double)(200f/player.maxHP) * player.HP);
        return player.HP > 0;
    }
    void endBattle(){
        skill_one.setDisable(true);
        skill_two.setDisable(true);
        skill_three.setDisable(true);
        skill_four.setDisable(true);
        skill_rest.setDisable(true);
        player.saveStats();
        exitButton.setDisable(false);
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
    public void five() {takeSkill(new Skills(2, 1));}

    @FXML void useSkillNow() {
        if(tmpSkill == null){
            return;
        }
        if(!(Combat.enoughMana(player, tmpSkill) && Combat.enoughStamina(player, tmpSkill))){
            return;
        }
        player.mana -= tmpSkill.getCostMana();
        player.stamina -= tmpSkill.getCostStamina();
        actionDependsOnSkillType(tmpSkill);
        if(!endOfFight){
            endRound();
        }
    }
    public void endRound() {
        fightHistory.appendText("\n");
        fightHistory.appendText("Round " + roundCounter + " - Enemy turn\n");
        enemyOffensiveSkill(skillsList.get(0), enemy, player);
        if(endOfFight){
            return;
        }
        fightHistory.appendText("\n");
        roundCounter++;
        fightHistory.appendText("Round " + roundCounter + " - Player turn\n");
        updateStats();
    }
    public void switchToSceneMenu() throws IOException {
        Main.setScene("/resources/fxml/mainGameScene.fxml","");
    }
}
