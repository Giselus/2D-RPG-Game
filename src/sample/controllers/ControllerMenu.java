package sample.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import sample.Main;
import java.io.IOException;
import java.util.Objects;
public class ControllerMenu {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSceneMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneMenu.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToSceneCreator(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/fxml/characterScene.fxml"));
        Parent root= loader.load();
        Main.controller=loader.getController();
        //String css=this.getClass().getResource("styleCharacter.css").toExternalForm();
        String css = this.getClass().getResource("/resources/style/styleCharacter.css").toExternalForm();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(css);
        //scene.getStylesheets().add(css);
        stage.show();
    }
    public void switchToTesting(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneTestScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToInventory(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneInventory.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("resources/style/styleInventory.css").toExternalForm();
        stage.setScene(scene);
        scene.getStylesheets().add(css);
        stage.show();
    }
    public void switchToLoading(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneFight.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("TEST");
    }
    public void exitGame(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}