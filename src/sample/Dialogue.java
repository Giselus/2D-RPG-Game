package sample;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Dialogue {

    private static HashMap<String, Dialogue> dialogues = new HashMap<>();

    public String text;
    public Action action;
    public ArrayList<Pair<GameVariable,Boolean>> conditions;
    public Dialogue(String name,String text, Action action, Pair<GameVariable,Boolean> ... conditions){
        this.text = text;
        this.action = action;
        this.conditions = new ArrayList<>();
        this.conditions.addAll(Arrays.asList(conditions));

        dialogues.put(name,this);
    }

    public static Dialogue fetchByName(String name){
        if(dialogues.containsKey(name)){
            return dialogues.get(name);
        }
        return null;
    }

}
