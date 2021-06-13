package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.CharacterManager;
import sample.InventoryPlayer;
import sample.Main;
import sample.Mission;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerMission {
    @FXML AnchorPane anchorPane;
    @FXML Text missionContent;
    @FXML Text doIt;
    @FXML Text reward;
    @FXML Button exitButton;
    @FXML Button GetReward;
    public static ArrayList<Mission> missionList=new ArrayList<Mission>();
    @FXML
    public void initialize(){
        String descriptionM1="Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Curabitur erat arcu, pharetra quis tortor vitae, tempus efficitur nisl. " +
                "Sed at vulputate quam. Pellentesque tristique quam at turpis rutrum imperdiet.";

        //TODO delete next four lines
        if(missionList==null || missionList.isEmpty()){
            Mission M1=new Mission(1);
            Mission M2=new Mission(2);
            missionList.add(M1);
            missionList.add(M2);
        }
        int iter=0;
        for(Mission i: missionList){
            Rectangle rectangle=new Rectangle();
            //rectangle.setFill(Color.valueOf("#7e5b36"));
            rectangle.setFill(Color.rgb(126, 91, 54));
            rectangle.setX(53);
            rectangle.setY(154+73*iter);
            rectangle.setHeight(55);
            rectangle.setWidth(532);
            rectangle.setStroke(Color.rgb(0, 0, 0));
            Text nameText=new Text();
            nameText.setText(i.name);
            nameText.setX(80);
            nameText.setY(194+73*iter);
            nameText.setId("biggerText");
            Button buttonReadMore=new Button();
            buttonReadMore.setText("Read more");
            buttonReadMore.setLayoutX(439);
            buttonReadMore.setLayoutY(165+73*iter);
            buttonReadMore.setOnMousePressed(e->{
                missionContent.setText(i.description);
                String doItMission="";
                System.out.println(i.enemiesToKillArrayList.size());
                for(int j=0;j<i.enemiesToKillArrayList.size();j++){
                    doItMission+=i.enemiesToKillArrayList.get(j).getName();
                    doItMission+=": "+i.enemiesToKillArrayList.get(j).enemiesLeft;
                    doItMission+="/"+i.enemiesToKillArrayList.get(j).amountOfEnemies;
                    doItMission+="\n";
                }
                System.out.println(doItMission);
                doIt.setText(doItMission);
                String rewardToLabel = null;
                if(i.gold>0){
                    rewardToLabel=i.gold+" Gold\n";
                }
                if(i.exp>0){
                    rewardToLabel=rewardToLabel+i.exp+" Exp\n";
                }
                reward.setText(rewardToLabel);
                GetReward.setOnAction(e2->{
                    if(i.canBeFinished){
                        i.canBeFinished=false;
                        CharacterManager.instance.actualExp+=i.exp;
                        CharacterManager.instance.gold+=i.gold;
                        buttonReadMore.setText("Finished");
                        for(Mission j: missionList){
                            if(j.id==i.id){
                                missionList.remove(j);
                                return;
                            }
                        }
                    }
                });
            });
            anchorPane.getChildren().add(rectangle);
            anchorPane.getChildren().add(nameText);
            anchorPane.getChildren().add(buttonReadMore);
            iter++;
        }

    }
    public void switchToSceneMenu(ActionEvent event) throws IOException {
        Main.setScene("/resources/fxml/mainGameScene.fxml","");
    }
}
