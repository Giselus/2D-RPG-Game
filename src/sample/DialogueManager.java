package sample;

import javafx.scene.input.KeyCode;
import javafx.util.Pair;
import sample.controllers.ControllerContainer;
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
    public void setState(String state){
        currentState = DialogueState.FetchByName(state);
        activeDialogues.clear();
        ArrayList<String> tmp = new ArrayList<>();
        for(Dialogue dialogue: currentState.dialogueOptions){
            boolean flag = true;
            for(Pair<String,Boolean> condition:dialogue.conditions){
                if(!condition.getValue().equals(GameVariable.FetchByName(condition.getKey()).state))
                    flag = false;
            }
            if(flag){
                activeDialogues.add(dialogue);
                tmp.add(dialogue.text);
            }
        }
        mainGameController.instance.setDialogues(currentState.text,tmp);
    }

    public void OpenDialogue(String state){
        opened = true;
        mainGameController.instance.openDialogueBox();
        setState(state);
    }

    public void CloseDialogues(){
        opened = false;
        mainGameController.instance.closeDialogueBox();
    }

    public void Update(float deltaTime){
        if(activeDialogues.size() >= 1 && KeyPolling.pressedDown(KeyCode.DIGIT1)){
            activeDialogues.get(0).action.apply();
        }else if(activeDialogues.size() >= 2 && KeyPolling.pressedDown(KeyCode.DIGIT2)){
            activeDialogues.get(1).action.apply();
        }else if(activeDialogues.size() >= 3 && KeyPolling.pressedDown(KeyCode.DIGIT3)){
            activeDialogues.get(2).action.apply();
        }else if(activeDialogues.size() >= 4 && KeyPolling.pressedDown(KeyCode.DIGIT4)){
            activeDialogues.get(3).action.apply();
        }else if(activeDialogues.size() >= 5 && KeyPolling.pressedDown(KeyCode.DIGIT5)){
            activeDialogues.get(4).action.apply();
        }else if(activeDialogues.size() >= 6 && KeyPolling.pressedDown(KeyCode.DIGIT6)){
            activeDialogues.get(5).action.apply();
        }else if(activeDialogues.size() >= 7 && KeyPolling.pressedDown(KeyCode.DIGIT7)){
            activeDialogues.get(6).action.apply();
        }else if(activeDialogues.size() >= 8 && KeyPolling.pressedDown(KeyCode.DIGIT8)){
            activeDialogues.get(7).action.apply();
        }else if(activeDialogues.size() >= 9 && KeyPolling.pressedDown(KeyCode.DIGIT9)){
            activeDialogues.get(8).action.apply();
        }
    }

    public void Initialize(){
        //region Alchemist
        new Dialogue("AlchemistDealer1a","Sure, show me what do you have",
                () -> DialogueManager.instance.setState("AlchemistDealer2b"),
                new Pair<>("AlchemistDealerTalk",false));
        new Dialogue("AlchemistDealer2a","Uhh, I rather meant potions",
                () -> {
                    GameVariable.FetchByName("AlchemistDealerTalk").state = true;
                    DialogueManager.instance.setState("AlchemistDealer3b"); }
                );
        new Dialogue("AlchemistDealerNo", "Leave me alone you freak",
                this::CloseDialogues);
        new Dialogue("AlchemistDealerTrade","(Trade)",
                () -> {
                    CloseDialogues();
                    ControllerContainer.swapChest = ContainerForNpc.FetchByName("AlchemistShop");
                    Updatable.clearUpdatables();
                    Main.setScene("/resources/fxml/sceneContainer.fxml",
                            "/resources/style/styleContainer.css");
                }, new Pair<>("AlchemistDealerTalk",true));

        new DialogueState("AlchemistDealer1b",
                "Psst, hey. Do you wanna buy some fancy alchemical stuff?",
                "AlchemistDealer1a","AlchemistDealerTrade","AlchemistDealerNo");
        new DialogueState("AlchemistDealer2b",
                "Okay, I have cananbis, amphetamines, cocaine, heroine. " +
                        "Of course everything is high quality",
                "AlchemistDealer2a");
        new DialogueState("AlchemistDealer3b",
                "Sure... That's what I meant too...",
                "AlchemistDealerTrade");
        //endregion
        //region CityGuard
        new Dialogue("CityGuard1a","(Exit)",this::CloseDialogues);

        new DialogueState("CityGuard1b","Don't bother me, don't you see I'm busy?!",
                "CityGuard1a");
        //endregion
        //region Armourer
        new Dialogue("ArmourerTrade","Let me take a look at you wares",()->
            {
                this.CloseDialogues();
                ControllerContainer.swapChest = ContainerForNpc.FetchByName("ArmourerShop");
                Updatable.clearUpdatables();
                Main.setScene("/resources/fxml/sceneContainer.fxml",
                        "/resources/style/styleContainer.css");
            });
        new Dialogue("ArmourerExit","I cannot afford anything right now",()->
                DialogueManager.instance.setState("Armourer2b"));
        new Dialogue("ArmourerExit2","(Exit)",this::CloseDialogues);
        new DialogueState("Armourer1b","You should know that we have best equipment in this city",
                "ArmourerTrade","ArmourerExit");
        new DialogueState("Armourer2b","So don't bother me, come back when you have some coins," +
                " I'm not charity organisation",
                "ArmourerExit2");

        //endregion

        //new Dialogue("test","Chcę punkty!!", ()->DialogueManager.instance.setState("test2"));
        //new Dialogue("test2","(Zamknij)", this::CloseDialogues);

        //new DialogueState("test","Greetings adventurer!",Dialogue.fetchByName("test"));
        //new DialogueState("test2","Jasna sprawa, masz tu 20 (you've got 20 points)!",
          //      Dialogue.fetchByName("test2"));


    }
}
