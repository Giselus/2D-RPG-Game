package sample;

import java.util.HashMap;

public class GameVariable {

    public boolean state;

    public static HashMap<String, GameVariable> variables;

    GameVariable(String name){
        variables.put(name,this);
    }

    public GameVariable getVariable(String name){
        if(variables.containsKey(name)){
            return variables.get(name);
        }else{
            System.out.println("Value not present in hashMap");
            return null;
        }
    }

    public static void Initialize(){
        //TODO: Initialize some variables
    }
}
