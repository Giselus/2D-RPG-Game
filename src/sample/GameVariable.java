package sample;

import java.util.HashMap;

public class GameVariable {

    public boolean state;

    public static HashMap<String, GameVariable> variables = new HashMap<>();

    GameVariable(String name){
        variables.put(name,this);
    }

    public static GameVariable FetchByName(String name){
        if(variables.containsKey(name)){
            return variables.get(name);
        }else{
            System.out.println("Value not present in hashMap");
            return null;
        }
    }

    public static void Initialize(){
        new GameVariable("AlchemistDealerTalk");
        new GameVariable("FatherFirstTalk");
        new GameVariable("SatoriDialogue");
        new GameVariable("BossAlive");
        new GameVariable("QuestAccepted");
    }
}
