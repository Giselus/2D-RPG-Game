package sample;

public class MainCharacter {
    String name;
    int attack;
    int defense;
    int luck;
    int agility;
    int hp;
    //another useful stats:
    //int mana;
    //int stamina;
    public MainCharacter(String _name,int _attack,int _defense,int _luck,int _agility){
        name=_name;
        attack=_attack;
        defense=_defense;
        luck=_luck;
        agility=_agility;
        hp=100;
    }
}
