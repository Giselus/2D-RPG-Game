package sample;

import javafx.scene.input.KeyCode;
import javafx.util.Pair;
import sample.controllers.mainGameController;

import java.util.ArrayList;

public class DialogueManager extends Updatable{

    private DialogueState currentState;
    public static DialogueManager instance;
    public boolean opened;
    public DialogueManager(){
        if(instance == null){
            instance = this;
            Initialize();
        }
    }
    private ArrayList<Dialogue> activeDialogues = new ArrayList<>();
    public void setState(DialogueState state){
        currentState = state;
        activeDialogues.clear();
        ArrayList<String> tmp = new ArrayList<>();
        for(Dialogue dialogue: state.dialogueOptions){
            boolean flag = true;
            for(Pair<GameVariable,Boolean> condition:dialogue.conditions){
                if(condition.getKey().state != condition.getValue())
                    flag = false;
            }
            if(flag){
                activeDialogues.add(dialogue);
                tmp.add(dialogue.text);
            }
        }
        mainGameController.instance.setDialogues(state.text,tmp);
    }

    public void OpenDialogue(DialogueState state){
        opened = true;
        mainGameController.instance.openDialogueBox();
        setState(state);
    }

    public void CloseDialogues(){
        opened = false;
        mainGameController.instance.closeDialogueBox();
    }

    public void Update(float deltaTime){
        //TODO: check if any option is chosen
        if(activeDialogues.size() >= 1 && KeyPolling.isDown(KeyCode.DIGIT1)){
            activeDialogues.get(0).action.apply();
        }else if(activeDialogues.size() >= 2 && KeyPolling.isDown(KeyCode.DIGIT2)){
            activeDialogues.get(1).action.apply();
        }else if(activeDialogues.size() >= 3 && KeyPolling.isDown(KeyCode.DIGIT3)){
            activeDialogues.get(2).action.apply();
        }else if(activeDialogues.size() >= 4 && KeyPolling.isDown(KeyCode.DIGIT4)){
            activeDialogues.get(3).action.apply();
        }else if(activeDialogues.size() >= 5 && KeyPolling.isDown(KeyCode.DIGIT5)){
            activeDialogues.get(4).action.apply();
        }else if(activeDialogues.size() >= 6 && KeyPolling.isDown(KeyCode.DIGIT6)){
            activeDialogues.get(5).action.apply();
        }else if(activeDialogues.size() >= 7 && KeyPolling.isDown(KeyCode.DIGIT7)){
            activeDialogues.get(6).action.apply();
        }else if(activeDialogues.size() >= 8 && KeyPolling.isDown(KeyCode.DIGIT8)){
            activeDialogues.get(7).action.apply();
        }else if(activeDialogues.size() >= 9 && KeyPolling.isDown(KeyCode.DIGIT9)){
            activeDialogues.get(8).action.apply();
        }
    }

    public void Initialize(){
        //Dialogues
        new Dialogue("test","This is just test", this::CloseDialogues);

        //States
        new DialogueState("test","Greetings adventurer!",Dialogue.fetchByName("test"));


    }
}
