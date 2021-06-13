package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DialogueState {

    public String name;
    public String text;
    public ArrayList<Dialogue> dialogueOptions;

    public static HashMap<String,DialogueState> dialogueStates = new HashMap<>();

    public DialogueState(String name, String text, String ... options){
        this.text = text;
        dialogueOptions = new ArrayList<>();
        for(String string: options){
            dialogueOptions.add(Dialogue.fetchByName(string));
        }
        dialogueStates.put(name,this);
    }

    public static DialogueState FetchByName(String name){
        if(dialogueStates.containsKey(name)){
            return dialogueStates.get(name);
        }
        return null;
    }

}
