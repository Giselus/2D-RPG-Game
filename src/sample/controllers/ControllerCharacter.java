package sample.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.CharacterManager;
import sample.Main;
import sample.Updatable;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class ControllerCharacter extends Updatable {
    int attack=0;
    int defense=0;
    int skillPoint=10;
    int luck;
    int agility;
    //look variable
    boolean flagToDelete=false;
    int skinId=1;
    int hairId=0;
    int shirtId=0;
    int pantsId=0;
    float epsilonHair=0;
    Image imgSkin;
    ImageView imageViewSkin;
    Image imgHair;
    ImageView imageViewHair;
    Image imgShirt;
    ImageView imageViewShirt;
    Image imgPants;
    ImageView imageViewPants;
    String name;
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
        name=idName.getText();
        if((name!="" && skillPoint==0)||(true)){

            new CharacterManager(name,attack,defense,luck,agility,100,imgSkin,imgPants,
                    imgShirt,imgHair,0,0,3);
            Main.setScene("/resources/fxml/mainGameScene.fxml");
            this.setActive(false);
        }else{
            Alert alertWrongInfo = new Alert(Alert.AlertType.INFORMATION);
            alertWrongInfo.setTitle("Something is wrong!");
            alertWrongInfo.setHeaderText("Something is wrong!");
            //alert.setHeaderText("Look, an Information Dialog");
            if(skillPoint!=0 && name!=""){
                alertWrongInfo.setContentText("No skills");
                alertWrongInfo.showAndWait();
            }
            else if(skillPoint==0 && name==""){
                alertWrongInfo.setContentText("No name");
                alertWrongInfo.showAndWait();
            }
            else{
                alertWrongInfo.setContentText("No name and skills");
                alertWrongInfo.showAndWait();
            }
        }
    }
    float frame=0;
    @Override
    public void Update(float deltaTime){
        deleteSkin();
        deletePants();
        deleteHair();
        deleteShirt();
        startSkin(((int)(frame*10))%9);
        startHair(((int)(frame*10))%9);
        startPants(((int)(frame*10))%9);
        startShirt(((int)(frame*10))%9);
        frame+=deltaTime;
    }
    //look function:
    //body
    @FXML protected void leftSkin(ActionEvent e){
        skinId--;
        if(skinId==0){
            skinId=7;
        }
    }
    @FXML protected void rightSkin(ActionEvent e){
        skinId++;
        if(skinId%7==1){
            skinId=1;
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
        imageView.setX(625);
        imageView.setY(97);
        idAnchorPane.getChildren().add(imageView);
    }
    //hair:
    @FXML protected void leftHair(ActionEvent e){
        hairId--;
        if(hairId==-1){
            hairId=24;
        }
    }
    @FXML protected void rightHair(ActionEvent e){
        hairId++;
        if(hairId==25){
            hairId=0;
        }

    }
    public void deleteHair(){
        idAnchorPane.getChildren().remove(imageViewHair);
        idAnchorPane.getChildren().remove(imageViewHair);
    }
    public void startHair(int x){
        imgHair=new Image(String.valueOf(getClass().getResource("/resources/textures/character/hair/hair"+hairId+".png")));
        imageViewHair=new ImageView(imgHair);
        imageViewHair.setViewport(new Rectangle2D(x*64,64*10,64,64));
        addHair(imageViewHair);
    }
    public void addHair(ImageView imageView){
        imageView.setX(625.5);
        imageView.setY(96.5);
        idAnchorPane.getChildren().add(imageView);
    }
    //t-shirt
    @FXML protected void leftShirt(ActionEvent e){
        shirtId--;
        if(shirtId==-1){
            shirtId=7;
        }
        //System.out.println(shirtId);
    }
    @FXML protected void rightShirt(ActionEvent e){
        shirtId++;
        if(shirtId==8){
            shirtId=0;
        }
    }
    public void deleteShirt(){
        idAnchorPane.getChildren().remove(imageViewShirt);
        idAnchorPane.getChildren().remove(imageViewShirt);
    }
    public void startShirt(int x){
        imgShirt=new Image(String.valueOf(getClass().getResource("/resources/textures/character/t-shirt/t-shirt"+shirtId+".png")));
        imageViewShirt=new ImageView(imgShirt);
        imageViewShirt.setViewport(new Rectangle2D(x*64,64*10,64,64));
        addHair(imageViewShirt);
    }
    public void addShirt(ImageView imageView){
        imageView.setX(625.5);
        imageView.setY(96.5);
        idAnchorPane.getChildren().add(imageView);
    }
    //pants:
    @FXML protected void leftPants(ActionEvent e){
        pantsId--;
        if(pantsId==-1){
            pantsId=5;
        }
    }
    @FXML protected void rightPants(ActionEvent e){
        pantsId++;
        if(pantsId==6){
            pantsId=0;
        }

    }
    public void deletePants(){
        idAnchorPane.getChildren().remove(imageViewPants);
        idAnchorPane.getChildren().remove(imageViewPants);
    }
    public void startPants(int x){
        imgPants=new Image(String.valueOf(getClass().getResource("/resources/textures/character/pants/pants"+pantsId+".png")));
        imageViewPants=new ImageView(imgPants);
        imageViewPants.setViewport(new Rectangle2D(x*64,64*10,64,64));
        addHair(imageViewPants);
    }
    public void addPants(ImageView imageView){
        imageView.setX(625.5);
        imageView.setY(96.5);
        idAnchorPane.getChildren().add(imageView);
    }
    //random
    @FXML protected void randomCharacter(ActionEvent e){
        Random random = new Random();
        skinId=random.nextInt(7)+1;
        hairId=random.nextInt(26);
        shirtId=random.nextInt(8);
        pantsId=random.nextInt(6);
        //System.out.println(skinId+" "+hairId+" "+shirtId+" "+pantsId);
        //pantsId=random.nextInt(8);
    }
    public void switchToSceneMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneMenu.fxml")));
        String css=this.getClass().getResource("style.css").toExternalForm();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(css);
        Main.controller = null;
        stage.show();
    }
}