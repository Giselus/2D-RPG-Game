package sample;

public class MainCharacter {
    String name;
    int attack;
    int defense;
    int luck;
    int agility;
    int hp;
    int skin;
    int legs;
    int body;
    int hair;
    //another useful stats:
    //int mana;
    //int stamina;
    public MainCharacter(String _name,int _attack,int _defense,int _luck,int _agility,int _skin,
                          int _legs,int _body,int _hair){
        name=_name;
        attack=_attack;
        defense=_defense;
        luck=_luck;
        agility=_agility;
        hp=100;
        skin=_skin;
        legs=_legs;
        body=_body;
        hair=_hair;
    }
}
