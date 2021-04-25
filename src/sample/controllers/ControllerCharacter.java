package sample.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;
import sample.MainCharacter;

import java.io.IOException;
import java.lang.reflect.AnnotatedArrayType;
import java.util.Objects;

public class ControllerCharacter {
    String name;
    int attack=0;
    int defense=0;
    int skillPoint=10;
    int luck;
    int agility;
    //look variable
    boolean flagToDelete=false;
    int skinId=1;
    int hairId=1;
    Image imgSkin;
    ImageView imageViewSkin;
    Image imgHair;
    ImageView imageViewHair;
    //----
    //image:
//    Image img=new Image(String.valueOf(getClass().getResource("/resources/textures/character/skin/skin"+skinId+".png")));
//    ImageView imageView2=new ImageView(img);
//    imageView2.setViewport(new Rectangle2D(5,2+64*6,48,60));
    //--
    @FXML private TextField idName;
    @FXML private Text idSkillPoint;
    @FXML private Text idAttackPoint;
    @FXML private Text idDefensePoint;
    @FXML private Text idLuckPoint;
    @FXML private Text idAgilityPoint;
    @FXML private AnchorPane idAnchorPane;
    @FXML public void addPointAttack(ActionEvent e){
        if(skillPoint>0){
            //System.out.println("TEST");
            skillPoint--;
            idSkillPoint.setText(String.valueOf(skillPoint));
            attack++;
            idAttackPoint.setText(String.valueOf(attack));
        }
    }
    @FXML protected void subPointAttack(ActionEvent e){
        if(attack>0){
            attack--;
            skillPoint++;
            idSkillPoint.setText(String.valueOf(skillPoint));
            idAttackPoint.setText(String.valueOf(attack));
        }
    }
    @FXML protected  void addPointDefense(ActionEvent e){
        if(skillPoint>0){
            skillPoint--;
            defense++;
            idSkillPoint.setText(String.valueOf(skillPoint));
            idDefensePoint.setText(String.valueOf(defense));
        }
    }
    @FXML protected void subPointDefense(ActionEvent e){
        if(defense>0){
            defense--;
            skillPoint++;
            idSkillPoint.setText(String.valueOf(skillPoint));
            idDefensePoint.setText(String.valueOf(defense));
        }
    }
    @FXML protected void addPointAgility(ActionEvent e){
        if(skillPoint>0){
            skillPoint--;
            agility++;
            idSkillPoint.setText(String.valueOf(skillPoint));
            idAgilityPoint.setText(String.valueOf(agility));
        }
    }
    @FXML protected void subPointAgility(ActionEvent e){
        if(agility>0){
            agility--;
            skillPoint++;
            idSkillPoint.setText(String.valueOf(skillPoint));
            idAgilityPoint.setText(String.valueOf(agility));
        }
    }
    @FXML protected void addPointLuck(ActionEvent e){
        if(skillPoint>0){
            skillPoint--;
            luck++;
            idSkillPoint.setText(String.valueOf(skillPoint));
            idLuckPoint.setText(String.valueOf(luck));
        }
    }
    @FXML protected void subPointLuck(ActionEvent e){
        if(luck>0){
            luck--;
            skillPoint++;
            idSkillPoint.setText(String.valueOf(skillPoint));
            idLuckPoint.setText(String.valueOf(luck));
        }
    }
    @FXML protected void startGame(ActionEvent e){
        MainCharacter character=new MainCharacter(name,attack,defense,luck,agility);
        //save to file
    }
    float frame=0;
    public void move(float deltaTime){
        deleteSkin();
        startSkin(((int)(frame*10))%9);
        //startHair();
        frame+=deltaTime;


    }
    //look function:
    @FXML protected void leftSkin(ActionEvent e){
        if(skinId>1){
            skinId--;
        }
    }
    @FXML protected void rightSkin(ActionEvent e){
        if(skinId<7){
            skinId++;
        }
        //startHair();
    }
    public void deleteSkin(){
        idAnchorPane.getChildren().remove(imageViewSkin);
        idAnchorPane.getChildren().remove(imageViewSkin);
    }
    public void startSkin(int x){
        //image:
         imgSkin=new Image(String.valueOf(getClass().getResource("/resources/textures/character/skin/skin"+skinId+".png")));
        imageViewSkin=new ImageView(imgSkin);
        imageViewSkin.setViewport(new Rectangle2D(x*64,64*10,64,64));
        //--
        addSkin(imageViewSkin);
    }
    public void addSkin(ImageView imageView){
        imageView.setX(800);
        imageView.setY(100);
        idAnchorPane.getChildren().add(imageView);
    }
    @FXML protected void leftHair(ActionEvent e){
        if(hairId>0){
            hairId--;
        }
        if(hairId==0){
            //usuwanie wlosow
        }

    }
    @FXML protected void rightHair(ActionEvent e){
        if(hairId<10){
            hairId++;
        }
    }
    public void startHair(){
        imgHair=new Image(String.valueOf(getClass().getResource("/resources/textures/character/hair/hair1.png")));
        imageViewHair=new ImageView(imgHair);
        addHair(imageViewHair);
    }
    public void addHair(ImageView imageView){
        imageView.setX(795);
        imageView.setY(98);
        idAnchorPane.getChildren().add(imageView);
    }
    public void switchToSceneMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Main.controller = null;
        stage.show();
    }
}