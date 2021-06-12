package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.InventoryPlayer;
import sample.Mission;

import java.util.ArrayList;

public class ControllerMission {
    @FXML AnchorPane anchorPane;
    @FXML Text missionContent;
    @FXML Text doIt;
    @FXML Text reward;
    public static ArrayList<Mission> missionList=new ArrayList<Mission>();
    @FXML
    public void initialize(){
        String descriptionM1="Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Curabitur erat arcu, pharetra quis tortor vitae, tempus efficitur nisl. " +
                "Sed at vulputate quam. Pellentesque tristique quam at turpis rutrum imperdiet.";
        Mission M1=new Mission(1);
        Mission M2=new Mission(2);
        missionList.add(M1);
        missionList.add(M2);
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
                String doItMission=null;
                for(int j=0;j<i.enemiesToKillArrayList.size();j++){
                    doItMission=i.enemiesToKillArrayList.get(j).getName();
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
            });
            anchorPane.getChildren().add(rectangle);
            anchorPane.getChildren().add(nameText);
            anchorPane.getChildren().add(buttonReadMore);
            iter++;
        }

    }
}
