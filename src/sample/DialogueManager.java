package sample;

import javafx.scene.input.KeyCode;
import javafx.util.Pair;
import sample.controllers.ControllerContainer;
import sample.controllers.ControllerFight;
import sample.controllers.ControllerMission;
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
        //region Father
        new Dialogue("TalkFirstTime","(Talk)",()->{
            DialogueManager.instance.setState("Father1b");
            ControllerMission.missionList.add(new Mission(1));
            GameVariable.FetchByName("FatherFirstTalk").state = true;},
                new Pair<>("FatherFirstTalk",false));
        new Dialogue("TalkNextTime","(Talk)",()->
                DialogueManager.instance.setState("Father1c"),
                new Pair<>("FatherFirstTalk",true));
        new Dialogue("ExitFather","(Leave him)",this::CloseDialogues);
        new Dialogue("Father1a","(Continue)",()->
                DialogueManager.instance.setState("Father2b"));
        new Dialogue("Father2a","Okay dad, I’m going for an adventure!",()->
                DialogueManager.instance.setState("Father3b"));


        new DialogueState("FatherStartPhase","","TalkFirstTime","TalkNextTime","ExitFather");
        new DialogueState("Father1b","Oh son, it’s high time you left your room. " +
                "I have a special mission for you. In the North there is a big city. " +
                "You will find a real job and you will stop sitting in front of a PC all day long."
                ,"Father1a");
        new DialogueState("Father2b", "All the information will be available on a job board. " +
                "Additionally, the monsters in our neighbourhood are destroying our crops. " +
                "Take care of that as well.","Father2a");

        new DialogueState("Father3b","Don’t forget to take some clothes from the wardrobe. " +
                "You don’t want to catch a cold, do you?","ExitFather");
        new DialogueState("Father1c","I already told you what to do! Go now!","ExitFather");
        //endregion
        //region Satori
        new Dialogue("SatoriExit","(Exit)",()->
        {
            GameVariable.FetchByName("SatoriDialogue").state = true;
            CloseDialogues();
        });
        new DialogueState("Satori",
                "It’s happening again! Satori has been checking my solution for 30 minutes. " +
                        "At least I have time to go outside. I’m gonna see what my dad’s doing.",
                "SatoriExit");
        //endregion
        //region Boss
        new Dialogue("Boss1a", "(Continue)",
                () -> DialogueManager.instance.setState("Boss2b"));
        new Dialogue("BossFight", "(Fight)",()->{
            DialogueManager.instance.CloseDialogues();
            ControllerFight.opponent = new Enemy(1,3);
            Updatable.clearUpdatables();
            Main.setScene("/resources/fxml/sceneFight.fxml","/resources/style/styleFight.css");
        });
        new DialogueState("Boss1b",
                "It turned out that you’ve found me. " +
                        "Now I can reveal my secret! I am the one slowing down Satori!!! " +
                        "If you think you can defeat me, you are dead wrong.", "Boss1a");
        new DialogueState("Boss2b",
                "You will never get 20 points for this project. " +
                        "I’ll do my best to stop you and I will defeat you right here and right now.",
                "BossFight");
        //endregion
        //region Quests
        new Dialogue("QuestsExit","(Exit)",this::CloseDialogues);
        new Dialogue("Quests1a","(Search for job)",()->{
            GameVariable.FetchByName("QuestAccepted").state = true;
            ControllerMission.missionList.add(new Mission(2));
            DialogueManager.instance.setState("Quests1b");
        },
        new Pair<>("QuestAccepted",false));
        new Dialogue("Quests2a","(Search for job)",()->{
            DialogueManager.instance.setState("Quests1c");
        },
        new Pair<>("QuestAccepted",true));

        new DialogueState("QuestsStart","You see plenty of yellowed letters pinned to" +
                " this old board.","Quests1a","Quests2a","QuestsExit");
        new DialogueState("Quests1c","There's really nothing that suits you",
                "QuestsExit");
        new DialogueState("Quests1b","You found interesting order for few monsters's head. " +
                "It should be easy!","QuestsExit");
        //endregion
        //region BossChest
        new Dialogue("BossChest1a","(Continue)",()->
                DialogueManager.instance.setState("BossChest2b"));
        new Dialogue("BossChest2a","(Open chest)",()->{
            ControllerContainer.swapChest = ContainerForNpc.FetchByName("ChestReward");
            Updatable.clearUpdatables();
            CloseDialogues();
            Main.setScene("/resources/fxml/sceneContainer.fxml",
                    "/resources/style/styleContainer.css");
        });
        new DialogueState("BossChest1b",
                "Yeah! I’ve managed to overcome my biggest fear and I’ve scored 20 out of 20 points. " +
                        "Besides that, the submit on Satori got “OK”.", "BossChest1a");
        new DialogueState("BossChest2b",
                "What a crazy day! I have to come back home and tell my father about everything!",
                "BossChest2a");

        //endregion
    }
}
