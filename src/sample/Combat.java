package sample;

import java.util.concurrent.ThreadLocalRandom;

public class Combat {

    public static int countDamage(int A_attack, int A_luck, int B_defense, Skills skill){
        float damage = skill.getDamage() + A_attack*skill.getModifier();
        int littleRandomness = ThreadLocalRandom.current().nextInt((int)-damage/10, (int)damage/10+1);
        damage += littleRandomness;
        int randoms = ThreadLocalRandom.current().nextInt(0, 1001);
        if(randoms <= 100+A_luck){
            damage *= 2f;
        }
        damage = damage * 100f/(100f + B_defense);
        return (int)damage;
    }

    public static boolean hitLanded(int A_agility, int B_agility){
        int result = 5*(A_agility - B_agility);
        int randoms = ThreadLocalRandom.current().nextInt(0, 1001);
        return randoms <= Math.min(900 + result, 990);
    }

    public static boolean enoughMana(combatStats user, Skills skills){
        return user.mana >= skills.costMana;
    }
    public static boolean enoughStamina(combatStats user, Skills skills){
        return user.stamina >= skills.costStamina;
    }
    public static String healAmount(Skills s){
        return "You are using " + s.getNameOfSkill() + " and feel that your wounds are healing. You restore "+ s.getPlusHealth() +" HP.\n";
    }
    public static String buffsToString(Skills s){
        return "You are using "+ s.getNameOfSkill() +" and feel stronger. Your attack increases.\n";
    }
    public static String damageToString(Skills s, int damage){
        return "You are using " + s.getNameOfSkill() + " and you are dealing " + damage +" damage.\n";
    }
    public static String EdamageToString(Skills s, int damage){
        return "Enemy is using " + s.getNameOfSkill() + " and deals " + damage +" damage.\n";
    }
    public static String showEnemyHp(int enemyHP){
        return "Your enemy has " + enemyHP + " HP left.\n";
    }
    public static String showPlayerHp(int playerHP){
        return "You have " + playerHP + " HP left.\n";
    }
    public static String winningMessage(){
        return "You won! Your enemy is dead.\n";
    }
    public static String losingMessage(){
        return "You lost! You are dead.\n";
    }

    public static class combatStats{
        public int attack;
        public int defense;
        public int agility;
        public int stamina;
        public int mana;
        public int luck;
        public int maxHP;
        public int HP;
        public int maxMana;
        public int maxStamina;
        public boolean isUser;
        public combatStats(){
            this.agility = 0;
            this.attack = 0;
            this.defense = 0;
            this.stamina = 0;
            this.luck = 0;
            this.mana = 0;
            this.HP = 100;
            this.maxHP = 100;
            this.maxMana = 0;
            this.maxStamina = 0;
            this.isUser = false;
        }
        public combatStats(boolean tmp){
            this.agility = CharacterManager.instance.agility+CharacterManager.instance.agilityItems;
            this.attack = CharacterManager.instance.attack+CharacterManager.instance.attackItems;
            this.defense = CharacterManager.instance.defense+CharacterManager.instance.defenseItems;
            this.stamina = CharacterManager.instance.current_stamina;
            this.luck = CharacterManager.instance.luck+CharacterManager.instance.luckItems;
            this.mana = CharacterManager.instance.current_mana;
            this.HP = CharacterManager.instance.current_hp;
            this.maxHP = CharacterManager.instance.hp+CharacterManager.instance.hpItems;
            this.maxMana = CharacterManager.instance.mana*10+CharacterManager.instance.manaItems*10 + 100;
            this.maxStamina = CharacterManager.instance.stamina*10 + CharacterManager.instance.staminaItems*10 + 100;
            this.isUser = true;
        }

        public void saveStats(){
            CharacterManager.instance.current_hp = HP;
            CharacterManager.instance.current_mana = mana;
            CharacterManager.instance.current_stamina = stamina;
        }
    }
}
